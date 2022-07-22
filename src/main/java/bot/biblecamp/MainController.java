package bot.biblecamp;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping
    public ResponseEntity<String> getBootName() {
        return ResponseEntity.ok()
                             .contentType(MediaType.TEXT_PLAIN)
                             .body("BibleCamp 2022");
    }
}
