package com.jongkeun.edu_system.controller;

import com.jongkeun.edu_system.model.Teacher;
import com.jongkeun.edu_system.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherRepository teacherRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("teachers", teacherRepository.findAll());

        return "teacher-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("teacher", new Teacher());

        return "teacher-form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Teacher teacher) {
        teacherRepository.save(teacher);

        return "redirect:/teachers";
    }

    @GetMapping("/edit/{id}")
    public String editFrom(@PathVariable int id, Model model){
        model.addAttribute("teacher", teacherRepository.findById(id));

        return "teacher-form";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Teacher teacher){
        teacherRepository.update(teacher);

        return "redirect:/teachers";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        try {
            int affected = teacherRepository.delete(id);

            if (affected == 0){
                System.out.println("해당 교사를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
//            model.addAttribute("error", "에러 :" + e.getMessage());
            // 학생이 있는 교사 삭제 시도 시
            System.out.println("에러: " + e.getMessage());
        }

        return "redirect:/teachers";
    }
}