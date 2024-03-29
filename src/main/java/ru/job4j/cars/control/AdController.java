package ru.job4j.cars.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.*;
import ru.job4j.cars.model.Ad;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@ThreadSafe
@Controller
public class AdController {

    private final AdService adService;

    private final CarService carService;

    private final EngineService engineService;

    private final BrandService brandService;

    private final BodyService bodyService;

    private final CategoryService categoryService;

    private final ModelService modelService;

    private final FilterService filterService;

    public AdController(AdService adService,
                        CarService carService,
                        EngineService engineService,
                        BrandService brandService,
                        BodyService bodyService,
                        CategoryService categoryService,
                        ModelService modelService,
                        FilterService filterService) {
        this.adService = adService;
        this.carService = carService;
        this.engineService = engineService;
        this.brandService = brandService;
        this.bodyService = bodyService;
        this.categoryService = categoryService;
        this.modelService = modelService;
        this.filterService = filterService;
    }

    public void defaultUser(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
    }

    @GetMapping("/ads")
    public String ads(Model model, HttpSession session) {
        defaultUser(model, session);
        model.addAttribute("ads", adService.findSoldAll(false));
        return "ads";
    }

    @GetMapping("/formSelectCategory")
    public String formSelectCategory(Model model, HttpSession session) {
        defaultUser(model, session);
        model.addAttribute("category", categoryService.findAll());
        return "selectCategory";
    }

    @PostMapping("/selectCategory")
    public String selectCategory(@ModelAttribute Ad ad,
                               HttpSession session,
                               @RequestParam("category.id") String idCategory) {
        User user = (User) session.getAttribute("user");
        ad.setUser(user);
        filterService.setCategory(Integer.parseInt(idCategory));
        return "redirect:/formSelectBody";
    }

    @GetMapping("/formSelectBody")
    public String formSelectBody(Model model, HttpSession session) {
        defaultUser(model, session);
        model.addAttribute("body", bodyService.findBodyByCategory(filterService.getCategory()));
        return "selectBody";
    }

    @PostMapping("/selectBody")
    public String selectBody(@ModelAttribute Ad ad,
                               HttpSession session,
                               @RequestParam("body.id") String idBody) {
        User user = (User) session.getAttribute("user");
        ad.setUser(user);
        filterService.setBody(Integer.parseInt(idBody));
        return "redirect:/formSelectBrand";
    }

    @GetMapping("/formSelectBrand")
    public String formSelectBrand(Model model, HttpSession session) {
        defaultUser(model, session);
        model.addAttribute("brand", brandService.findAll());
        return "selectBrand";
    }

    @PostMapping("/selectBrand")
    public String saveBrand(@ModelAttribute Ad ad,
                                 HttpSession session,
                                 @RequestParam("brand.id") String idBrand) {
        User user = (User) session.getAttribute("user");
        ad.setUser(user);
        filterService.setBrand(Integer.parseInt(idBrand));
        return "redirect:/formSelectModel";
    }

    @GetMapping("/formSelectModel")
    public String formSelectModel(Model model, HttpSession session) {
        defaultUser(model, session);
        model.addAttribute("model", modelService.findModelByBrand(filterService.getBrand()));
        return "selectModel";
    }

    @PostMapping("/selectModel")
    public String saveModel(@ModelAttribute Ad ad,
                            HttpSession session,
                            @RequestParam("model.id") String idModel) {
        User user = (User) session.getAttribute("user");
        ad.setUser(user);
        filterService.setModel(Integer.parseInt(idModel));
        return "redirect:/formSelectEngine";
    }

    @GetMapping("/formSelectEngine")
    public String formSelectEngine(Model model, HttpSession session) {
        defaultUser(model, session);
        model.addAttribute("engine", engineService.findAll());
        return "selectEngine";
    }

    @PostMapping("/selectEngine")
    public String saveEngine(@ModelAttribute Ad ad,
                             @ModelAttribute Car car,
                             HttpSession session,
                             @RequestParam("engine.id") String idEngine) {
        User user = (User) session.getAttribute("user");
        ad.setUser(user);
        filterService.setEngine(Integer.parseInt(idEngine));
        car.setCategory(categoryService.findById(filterService.getCategory()));
        car.setBody(bodyService.findById(filterService.getBody()));
        car.setBrand(brandService.findById(filterService.getBrand()));
        car.setModel(modelService.findById(filterService.getModel()));
        car.setEngine(engineService.findById(filterService.getEngine()));
        carService.add(car);
        filterService.setCar(car.getId());
        return "redirect:/formAddDescription";
    }

    @GetMapping("/formAddDescription")
    public String formAddDescription(Model model, HttpSession session) {
        defaultUser(model, session);
        model.addAttribute("car", carService.findById(filterService.getCar()));
        return "addAd";
    }

    @PostMapping("/addDescription")
    public String saveDescription(@ModelAttribute Ad ad,
                                  HttpSession session,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        User user = (User) session.getAttribute("user");
        ad.setUser(user);
        ad.setCreated(new Date(System.currentTimeMillis()));
        ad.setCar(carService.findById(filterService.getCar()));
        if (file.getBytes().length > 0) {
            ad.setPhoto(file.getBytes());
        }
        adService.add(ad);
        return "redirect:/ads";
    }

    @GetMapping("/photoAd/{adId}")
    public ResponseEntity<Resource> download(@PathVariable("adId") Integer adId) throws IOException {
        byte[] bImage = Files.readAllBytes(Paths.get("images/noPhoto.jpg"));
        Ad ad = adService.findById(adId);
        ResponseEntity<Resource> response = ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(bImage));
        if (ad.getPhoto() != null) {
            response = ResponseEntity.ok()
                    .headers(new HttpHeaders())
                    .contentLength(ad.getPhoto().length)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new ByteArrayResource(ad.getPhoto()));
        }
        return response;
    }

    @GetMapping("/descriptionAd/{adId}")
    public String descriptionItem(Model model, @PathVariable("adId") int id, HttpSession session) {
        defaultUser(model, session);
        model.addAttribute("ad", adService.findById(id));
        return "descriptionAd";
    }

    @GetMapping("/adsNew")
    public String adsNew(Model model, HttpSession session) {
        defaultUser(model, session);
        model.addAttribute("ads", adService.findNewCar(true, false));
        return "ads";
    }

    @GetMapping("/adsNoNew")
    public String adsNoNew(Model model, HttpSession session) {
        defaultUser(model, session);
        model.addAttribute("ads", adService.findNewCar(false, false));
        return "ads";
    }

    @GetMapping("/adsSold")
    public String adsSold(Model model, HttpSession session) {
        defaultUser(model, session);
        model.addAttribute("ads", adService.findSoldAll(true));
        return "ads";
    }

    @GetMapping("/passenger")
    public String passenger(Model model, HttpSession session) {
        defaultUser(model, session);
        model.addAttribute("ads", adService.findACategoryAndSold("Легковые", false));
        return "ads";
    }

    @GetMapping("/commercial")
    public String commercial(Model model, HttpSession session) {
        defaultUser(model, session);
        model.addAttribute("ads", adService.findACategoryAndSold("Коммерческие", false));
        return "ads";
    }

    @GetMapping("/special")
    public String special(Model model, HttpSession session) {
        defaultUser(model, session);
        model.addAttribute("ads", adService.findACategoryAndSold("Спец. техника", false));
        return "ads";
    }

    @GetMapping("/myAds")
    public String myAds(Model model, HttpSession session) {
        defaultUser(model, session);
        User user = (User) model.getAttribute("user");
        if (user != null) {
            model.addAttribute("ads", adService.findMyAds(user.getEmail()));
        }
        return "ads";
    }

    @GetMapping("/formFilterCategory")
    public String formFilterCategory(Model model, HttpSession session) {
        defaultUser(model, session);
        filterService.setCategory(0);
        model.addAttribute("category", categoryService.findAll());
        return "filterCategory";
    }

    @PostMapping("/filterCategory")
    public String filterCategory(@ModelAttribute Ad ad,
                                 HttpSession session,
                                 @RequestParam("category.id") String idCategory) {
        User user = (User) session.getAttribute("user");
        ad.setUser(user);
        filterService.setCategory(Integer.parseInt(idCategory));
        return "redirect:/formFilterBody";
    }

    @GetMapping("/formFilterBody")
    public String formFilterBody(Model model, HttpSession session) {
        defaultUser(model, session);
        filterService.setBody(0);
        model.addAttribute("body", bodyService.findBodyByCategory(filterService.getCategory()));
        return "filterBody";
    }

    @PostMapping("/filterBody")
    public String filterBody(@ModelAttribute Ad ad,
                             HttpSession session,
                             @RequestParam("body.id") String idBody) {
        User user = (User) session.getAttribute("user");
        ad.setUser(user);
        filterService.setBody(Integer.parseInt(idBody));
        return "redirect:/formFilterBrand";
    }

    @GetMapping("/formFilterBrand")
    public String formFilterBrand(Model model, HttpSession session) {
        defaultUser(model, session);
        filterService.setBrand(0);
        model.addAttribute("brand", brandService.findAll());
        return "filterBrand";
    }

    @PostMapping("/filterBrand")
    public String filterBrand(@ModelAttribute Ad ad,
                              HttpSession session,
                              @RequestParam("brand.id") String idBrand) {
        User user = (User) session.getAttribute("user");
        ad.setUser(user);
        filterService.setBrand(Integer.parseInt(idBrand));
        return "redirect:/formFilterModel";
    }

    @GetMapping("/formFilterModel")
    public String formFilterModel(Model model, HttpSession session) {
        defaultUser(model, session);
        model.addAttribute("model", modelService.findModelByBrand(filterService.getBrand()));
        filterService.setModel(0);
        return "filterModel";
    }

    @PostMapping("/filterModel")
    public String filterModel(@ModelAttribute Ad ad,
                             HttpSession session,
                             @RequestParam("model.id") String idModel) {
        User user = (User) session.getAttribute("user");
        ad.setUser(user);
        filterService.setModel(Integer.parseInt(idModel));
        return "redirect:/formAdsFilter";
    }

    @GetMapping("/formAdsFilter")
    public String formAdsFilter(Model model, HttpSession session) {
        defaultUser(model, session);
        model.addAttribute("ads", adService.findAdCategoryAndBodyAndBrandAndModel(
                categoryService.findById(filterService.getCategory()).getName(),
                bodyService.findById(filterService.getBody()).getType(),
                brandService.findById(filterService.getBrand()).getName(),
                modelService.findById(filterService.getModel()).getName())
        );
        return "ads";
    }

    @GetMapping("/deleteAd/{adId}")
    public String deleteAd(Model model, @PathVariable("adId") int id, HttpSession session) {
        defaultUser(model, session);
        adService.delete(id);
        return "redirect:/ads";
    }

    @GetMapping("/setSold/{adId}")
    public String setSold(Model model, @PathVariable("adId") int id, HttpSession session) {
        defaultUser(model, session);
        adService.setSold(id);
        return "redirect:/ads";
    }

}