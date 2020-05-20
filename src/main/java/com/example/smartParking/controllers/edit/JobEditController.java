package com.example.smartParking.controllers.edit;

import com.example.smartParking.model.domain.JobPosition;
import com.example.smartParking.service.DataEditingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
@RequestMapping("/dataEdit")
public class JobEditController {

    @Autowired
    DataEditingService dataEditingService;

    @GetMapping("job")
    public String getJobEdit(Model model) {
        model.addAttribute("jobs", dataEditingService.getAllJobs());
        return "dataEditing/job/jobList";
    }

    @GetMapping("job/add")
    public String addJob(Model model) {
        return "dataEditing/job/addJob";
    }

    @PostMapping("auto/division/{division}")
    public String addJob(@PathVariable @Valid JobPosition job, BindingResult bindingResult, Model model) {
        boolean success = false;
        if(success) {
            return getJobEdit(model);
        }
        else return addJob(model);
    }

    @GetMapping("job/edit/{job}")
    public String editJob(@PathVariable JobPosition job, Model model) {
        model.addAttribute("job", job);
        return "dataEditing/job/editJob";
    }

    @PostMapping("job/edit/{jobId}")
    public String editJob(@PathVariable Long jobId, @Valid JobPosition changedJob, BindingResult bindingResult, Model model) {
        JobPosition job = dataEditingService.getJob(jobId).get();
        return editJob(job, model);
    }

    @GetMapping("job/delete/{jobId}")
    public String deleteJob(@PathVariable Long jobId, Model model) {
        return getJobEdit(model);
    }
}
