package org.pablog.pills.web;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.pablog.pills.domain.Day;
import org.pablog.pills.domain.User;
import org.pablog.pills.service.DayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/days")
@Controller
public class DayController {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired DayService dayService;
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "/current", produces = "text/html")
    public String current(@ActiveUser User activeUser, Model uiModel) {
		Day day = dayService.findByTheDateAndUser(new Date(), activeUser);
		
		if(day == null) {
			day = dayService.createDay(activeUser);
		}
        uiModel.addAttribute("day", day);
        
        return "days/show";
    }


	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") ObjectId id, Model uiModel) {
        uiModel.addAttribute("day", dayService.findById(id));
        uiModel.addAttribute("itemId", id);
        return "days/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@ActiveUser User activeUser, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("days", dayService.findByUser(activeUser, new PageRequest(firstResult / sizeNo, sizeNo)));
            float nrOfPages = (float) dayService.findByUser(activeUser).size() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("days", dayService.findByUser(activeUser));
        }
        return "days/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Day day, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, day);
            return "days/update";
        }
        uiModel.asMap().clear();
        dayService.save(day);
        return "redirect:/days/" + encodeUrlPathSegment(day.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") ObjectId id, Model uiModel) {
        populateEditForm(uiModel, dayService.findById(id));
        return "days/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") ObjectId id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Day day = dayService.findById(id);
        dayService.delete(day);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/days";
    }

	void populateEditForm(Model uiModel, Day day) {
        uiModel.addAttribute("day", day);
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
