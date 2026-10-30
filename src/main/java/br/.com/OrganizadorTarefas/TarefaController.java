
package br.com.organizatarefas;

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

        List<Map<String, Object>> tarefas = jdbc.queryForList(
            "SELECT t.id, t.titulo, t.descricao, t.data_vencimento, " +
            "t.status, t.categoria_id, c.nome AS categoria " +
            "FROM tarefas t " +
            "JOIN categorias c ON t.categoria_id = c.id " +
            "ORDER BY t.id DESC"
        );

        List<Map<String, Object>> categorias = jdbc.queryForList(
            "SELECT id, nome FROM categorias ORDER BY nome"
        );

        Integer total = jdbc.queryForObject(
            "SELECT COUNT(*) FROM tarefas", Integer.class
        );

        Integer pendentes = jdbc.queryForObject(
            "SELECT COUNT(*) FROM tarefas WHERE status = 'Pendente'",
            Integer.class
        );

        Integer emAndamento = jdbc.queryForObject(
            "SELECT COUNT(*) FROM tarefas WHERE status = 'Em andamento'",
            Integer.class
        );

        Integer concluidas = jdbc.queryForObject(
            "SELECT COUNT(*) FROM tarefas WHERE status = 'Concluída'",
            Integer.class
        );

        model.addAttribute("tarefas", tarefas);
        model.addAttribute("categorias", categorias);
        model.addAttribute("total", total);
        model.addAttribute("pendentes", pendentes);
        model.addAttribute("emAndamento", emAndamento);
        model.addAttribute("concluidas", concluidas);

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
