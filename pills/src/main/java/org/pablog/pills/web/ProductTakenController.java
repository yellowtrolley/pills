package org.pablog.pills.web;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.pablog.pills.domain.ProductTaken;
import org.pablog.pills.repositories.ProductTakenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/producttakens")
@Controller
public class ProductTakenController {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
    ProductTakenRepository productTakenRepository;
	
	@RequestMapping(value = "/{idProductTaken}/{time}/{done}", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@PathVariable("idProductTaken") ObjectId idProductTaken, @PathVariable("time") String time, @PathVariable("done") boolean done) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        ProductTaken pt = productTakenRepository.findById(idProductTaken);
        
        switch (time) {
			case "morning":
				pt.setMorning(done);
				break;
			case "midday":
				pt.setMidday(done);
				break;
			case "night":
				pt.setNight(done);
				break;
		}
        
        if (productTakenRepository.save(pt) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
