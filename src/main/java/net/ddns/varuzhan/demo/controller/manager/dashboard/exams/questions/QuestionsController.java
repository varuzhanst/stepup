package net.ddns.varuzhan.demo.controller.manager.dashboard.exams.questions;

import net.ddns.varuzhan.demo.dto.ExamAdditionDto;
import net.ddns.varuzhan.demo.dto.ExamShowDto;
import net.ddns.varuzhan.demo.dto.NewQuestionDto;
import net.ddns.varuzhan.demo.model.*;
import net.ddns.varuzhan.demo.service.prototype.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.TreeSet;


@Controller
public class QuestionsController {
    private final ManagersGroupsSubjectsService managersGroupsSubjectsService;
    private final UserService userService;
    private final GroupInfoService groupInfoService;
    private final SubjectInfoService subjectInfoService;
    private final ExamService examService;
    private final QuestionService questionService;

    public QuestionsController(ManagersGroupsSubjectsService managersGroupsSubjectsService, UserService userService, GroupInfoService groupInfoService, SubjectInfoService subjectInfoService, ExamService examService, QuestionService questionService) {
        this.managersGroupsSubjectsService = managersGroupsSubjectsService;
        this.userService = userService;
        this.groupInfoService = groupInfoService;
        this.subjectInfoService = subjectInfoService;
        this.examService = examService;
        this.questionService = questionService;
    }


    @PostMapping("/manager/dashboard/exams/{examId}/questions")
    public String addNewQuestion(Model model, @PathVariable String examId, @ModelAttribute NewQuestionDto newQuestionDto,@RequestParam("correct") String correctAnswer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        String fullName = user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName();
        model.addAttribute("full_name", fullName);
        Exam examById = examService.getExamById(examId);
        if (examById != null){
            if(examById.getManagersGroupsSubjects().getUser().equals(user)){
                Question question = new Question();
                question.setQuestionText(newQuestionDto.getQuestionText());
                question.setExam(examById);
                question.setOption1(newQuestionDto.getOption1());
                question.setOption2(newQuestionDto.getOption2());
                question.setOption3(newQuestionDto.getOption3());
                question.setOption4(newQuestionDto.getOption4());
                question.setCorrectOption(Integer.parseInt(correctAnswer));
                questionService.save(question);
                return "redirect:/manager/dashboard/exams/"+examId+"/questions";
            }
        }
        return "redirect:/error";

    }

    @GetMapping("/manager/dashboard/exams/{examId}/questions")
    public String loadExamQuestions(@PathVariable String examId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByEmail(authentication.getName());
        String fullName = manager.getFirstName() + " " + manager.getMiddleName() + " " + manager.getLastName();
        model.addAttribute("full_name", fullName);
        Exam examById = examService.getExamById(examId);
        if (examById != null) {
            if (examById.getManagersGroupsSubjects().getUser().equals(userService.getUserById(manager.getId().toString()))) {
                model.addAttribute("questions", questionService.getQuestionsOfExam(examById));
                model.addAttribute("exam", examById);
                model.addAttribute("newQuestion", new NewQuestionDto());
                return "manager/dashboard/exams/questions/questionsView";

            } else return "redirect:/error";
        } else return "redirect:/error";
    }
}
