package imageserver.database;


import imageserver.beans.DatabaseQuery;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class HelperEndpoints {
    private static Logger log = Logger.getLogger(HelperEndpoints.class);
    private ImageDAO imageDAO = new ImageDAO();

    @PostMapping("/database")
    public ResponseEntity resetImageDatabase(@RequestParam("command") String command) {
        if (command == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else if (command.equalsIgnoreCase("drop images")) {
            log.info("Made it to elseif");
            imageDAO.executeQuery("DELETE FROM imagemetadata;");
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/database", consumes = "application/json")
    public ResponseEntity executeSQL(@RequestBody DatabaseQuery payload) {
//        Gson gson = new Gson();
//
//        DatabaseQuery dbq = gson.fromJson(payload.toString(), DatabaseQuery.class);
        log.info(payload.toString());
        return ResponseEntity.ok().build();
    }


}
