
package br.com.organizatarefas;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TarefaController {

    private final JdbcTemplate jdbc;

    public TarefaController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/")
    public String listar(Model model) {

        // Busca as tarefas utilizando a VIEW que contém o JOIN
        List<Map<String, Object>> tarefas = jdbc.queryForList(
            "SELECT * FROM vw_tarefas_categoria ORDER BY id DESC"
        );

        List<Map<String, Object>> categorias = jdbc.queryForList(
            "SELECT id, nome FROM categorias ORDER BY nome"
        );

        // Busca o total de tarefas
        Long totalLong = jdbc.queryForObject(
            "SELECT total_tarefas FROM vw_total_categoria",
            Long.class
        );
        Integer total = totalLong != null ? totalLong.intValue() : 0;

        // Busca pendentes
        Long pendentesLong = jdbc.queryForObject(
            "SELECT COALESCE(MAX(quantidade), 0) FROM vw_tarefas_status WHERE status = ?",
            Long.class,
            "Pendente"
        );
        Integer pendentes = pendentesLong != null ? pendentesLong.intValue() : 0;

        // Busca em andamento
        Long emAndamentoLong = jdbc.queryForObject(
            "SELECT COALESCE(MAX(quantidade), 0) FROM vw_tarefas_status WHERE status = ?",
            Long.class,
            "Em andamento"
        );
        Integer emAndamento = emAndamentoLong != null ? emAndamentoLong.intValue() : 0;

        // Busca concluídas
        Long concluidasLong = jdbc.queryForObject(
            "SELECT COALESCE(MAX(quantidade), 0) FROM vw_tarefas_status WHERE status = ?",
            Long.class,
            "Concluída"
        );
        Integer concluidas = concluidasLong != null ? concluidasLong.intValue() : 0;

        // Busca a média de tarefas por categoria
        BigDecimal mediaDecimal = jdbc.queryForObject(
            "SELECT media_tarefas FROM vw_media_categoria",
            BigDecimal.class
        );
        Double mediaCategoria = mediaDecimal != null ? mediaDecimal.doubleValue() : 0.0;

        model.addAttribute("tarefas", tarefas);
        model.addAttribute("categorias", categorias);
        model.addAttribute("total", total);
        model.addAttribute("pendentes", pendentes);
        model.addAttribute("emAndamento", emAndamento);
        model.addAttribute("concluidas", concluidas);
        model.addAttribute("mediaCategoria", mediaCategoria);

        return "index";
    }

    @PostMapping("/salvar")
    public String salvar(
            @RequestParam String titulo,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) String dataVencimento,
            @RequestParam int categoriaId) {

        Date data = (dataVencimento == null || dataVencimento.isBlank())
                ? null : Date.valueOf(dataVencimento);

        jdbc.update(
            "INSERT INTO tarefas (titulo, descricao, data_vencimento, categoria_id) " +
            "VALUES (?, ?, ?, ?)",
            titulo, descricao, data, categoriaId
        );

        return "redirect:/";
    }

    @PostMapping("/editar")
    public String editar(
            @RequestParam int id,
            @RequestParam String titulo,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) String dataVencimento,
            @RequestParam String status,
            @RequestParam int categoriaId) {

        Date data = (dataVencimento == null || dataVencimento.isBlank())
                ? null : Date.valueOf(dataVencimento);

        jdbc.update(
            "UPDATE tarefas SET titulo = ?, descricao = ?, " +
            "data_vencimento = ?, status = ?, categoria_id = ? WHERE id = ?",
            titulo, descricao, data, status, categoriaId, id
        );

        return "redirect:/";
    }

    @PostMapping("/excluir")
    public String excluir(@RequestParam int id) {

        jdbc.update(
            "DELETE FROM tarefas WHERE id = ?", id
        );

        return "redirect:/";
    
    } 

}
