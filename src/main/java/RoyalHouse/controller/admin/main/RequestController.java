package RoyalHouse.controller.admin.main;

import RoyalHouse.model.Request;
import RoyalHouse.service.admin.main.RequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/main/requests")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("request", new Request());
        return "admin/main/create-request";
    }

    @PostMapping("/new")
    public String createRequest(@ModelAttribute Request request, Model model) {
        try {
            requestService.createRequest(request);
            return "redirect:/admin/main/requests";
        } catch (Exception e) {
            return "admin/main/create-request";
        }
    }

    @GetMapping
    public String getAllRequests(Model model) {
        List<Request> requests = requestService.getAllRequest();
        model.addAttribute("requests", requests);
        return "admin/main/request/requests";
    }

    @GetMapping("/pages")
    public String getRequests(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Page<Request> requestPage = requestService.findPaginated(PageRequest.of(page - 1, size));
        model.addAttribute("pageRequests", requestPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", requestPage.getTotalPages());

        return "admin/main/request/requests";
    }

    @GetMapping("/{id}")
    public String getRequests(@PathVariable Long id, Model model) {
        Request request = requestService.getRequestById(id);
        model.addAttribute("request", request);
        return "admin/main/request";
    }

    @GetMapping("/delete/{id}")
    public String deleteRequest(@PathVariable Long id, Model model) {
        try {
            requestService.deleteRequest(id);
            return "redirect:/admin/main/requests";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при удалении заявки: " + e.getMessage());
            return "admin/main/requests-list";
        }
    }
}
