package be.gerard.empires.web;

import be.gerard.empires.model.Empire;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("empires")
public class EmpireRestController {

    private static final List<Empire> EMPIRES = List.of(
            Empire.withId(1).andLabelAndColorCode("ROMAN", "#e6288d"),
            Empire.withId(2).andLabelAndColorCode("IBERIAN", "#dcdcdc"),
            Empire.withId(3).andLabelAndColorCode("HELLENE", "#f5eb37"),
            Empire.withId(4).andLabelAndColorCode("MINOAN", "#aac846"),
            Empire.withId(5).andLabelAndColorCode("HATTIAN", "#eb82b4"),
            Empire.withId(6).andLabelAndColorCode("ASSYRIAN", "#46c8f0"),
            Empire.withId(7).andLabelAndColorCode("EGYPTIAN", "#fadcbe"),
            Empire.withId(8).andLabelAndColorCode("CARTHAGINIAN", "#f59b3c"),
            Empire.withId(9).andLabelAndColorCode("CELT", "#37b45a")
    );

    @GetMapping
    private Flux<Empire> allEmpires() {
        return Flux.fromIterable(EMPIRES);
    }

}
