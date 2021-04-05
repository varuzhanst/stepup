package net.ddns.varuzhan.demo.controller.user.dashboard;

import net.ddns.varuzhan.demo.model.*;
import net.ddns.varuzhan.demo.service.prototype.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping
public class ExamAttemptController {
    private final ExamAttemptService examAttemptService;
    private final UserService userService;
    private final UserGroupInfoService userGroupInfoService;
    private final ExamAnswerService examAnswerService;
    private final ExamService examService;
    private final QuestionService questionService;

    public ExamAttemptController(ExamAttemptService examAttemptService, UserService userService, UserGroupInfoService userGroupInfoService, ExamAnswerService examAnswerService, ExamService examService, QuestionService questionService) {
        this.examAttemptService = examAttemptService;
        this.userService = userService;
        this.userGroupInfoService = userGroupInfoService;
        this.examAnswerService = examAnswerService;
        this.examService = examService;
        this.questionService = questionService;
    }

    @GetMapping("/user/dashboard/exams/{examId}/attempt")
    public String makeAttempt(@PathVariable String examId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        Exam examById = examService.getExamById(examId);
        if (examById != null) {
            if (examById.getManagersGroupsSubjects().getGroupInfo().equals(userGroupInfoService.getGroupInfoByUser(user).getGroupInfo())) {
                if (examAttemptService.getAttemptByUserAndExam(user, examById) == null) {
                    ExamAttempt examAttempt = new ExamAttempt();
                    examAttempt.setExam(examById);
                    examAttempt.setStartedAt(LocalDateTime.now());
                    examAttempt.setUser(user);
                    examAttemptService.save(examAttempt);
                    HashSet<Question> questionsOfAttempt = new HashSet<>(questionService.getQuestionsForExamAttempt(examById));
                    int n = 1;
                    for (Question q : questionsOfAttempt) {
                        ExamAnswer examAnswer = new ExamAnswer();
                        examAnswer.setExamAttempt(examAttempt);
                        examAnswer.setQuestion(q);
                        examAnswer.setRealNumber(n++);
                        examAnswerService.save(examAnswer);
                    }
                    return "redirect:/user/dashboard/exams/" + examId + "/attempt/questions/1";
                }

            }
        }
        return "redirect:/user/dashboard";
    }

    @GetMapping("/user/dashboard/exams/{examId}/attempt/questions/{questionRealNumber}")
    public String startExam(@PathVariable String examId, @PathVariable String questionRealNumber, HttpServletRequest request, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        String fullName = user.getFirstName()
                + " " + user.getMiddleName()
                + " " + user.getLastName();
        model.addAttribute("full_name", fullName);
        Exam examById = examService.getExamById(examId);
        if (examById != null) {
            if (examById.getManagersGroupsSubjects().getGroupInfo().equals(userGroupInfoService.getGroupInfoByUser(user).getGroupInfo())) {
                ExamAttempt attempt = examAttemptService.getAttemptByUserAndExam(user, examById);
                if (attempt != null && request.getHeader("Referer") != null && attempt.getFinishedAt() == null) {

                    try {
                        if (Integer.parseInt(questionRealNumber) == 1) {
                            model.addAttribute("previousButton", false);
                            model.addAttribute("nextButton", true);
                            model.addAttribute("finishButton", true);

                        } else if (Integer.parseInt(questionRealNumber) == examById.getCountOfQuestions()) {
                            model.addAttribute("previousButton", true);
                            model.addAttribute("nextButton", false);
                            model.addAttribute("finishButton", true);
                        } else {
                            model.addAttribute("previousButton", true);
                            model.addAttribute("nextButton", true);
                            model.addAttribute("finishButton", true);
                        }
                        ExamAnswer examAnswer = examAnswerService.getExamAnswerByAttemptAndRealNumber(attempt, Integer.parseInt(questionRealNumber));
                        model.addAttribute("question", examAnswer);
                        model.addAttribute("exam", examById);
                        return "user/dashboard/exam/examView";
                    } catch (Exception e) {
                        attempt.setFinishedAt(LocalDateTime.now());
                        examAttemptService.save(attempt);
                    }

                } else if (attempt != null && attempt.getFinishedAt() != null) {
                    attempt.setFinishedAt(LocalDateTime.now());
                    examAttemptService.save(attempt);
                }

            }

        }

        return "redirect:/user/dashboard";
    }

    @GetMapping("/user/dashboard/exams/{examId}/attempt/questions/{questionRealNumber}/next")
    public String nextQuestion(@PathVariable String examId, @PathVariable String questionRealNumber,HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());

        Exam examById = examService.getExamById(examId);
        if (examById != null) {
            if (examById.getManagersGroupsSubjects().getGroupInfo().equals(userGroupInfoService.getGroupInfoByUser(user).getGroupInfo())) {
                ExamAttempt attempt = examAttemptService.getAttemptByUserAndExam(user, examById);
                if (attempt != null && request.getHeader("Referer") != null && attempt.getFinishedAt() == null) {
                    if(Integer.parseInt(questionRealNumber)<attempt.getExam().getCountOfQuestions())
                        return "redirect:/user/dashboard/exams/" + examId + "/attempt/questions/" + (Integer.parseInt(questionRealNumber) + 1);
                    else return "redirect:/user/dashboard/exams/" + examId + "/attempt/questions/" + (Integer.parseInt(questionRealNumber));
                }

            }

        }
        return "redirect:/user/dashboard";
    }

    @GetMapping("/user/dashboard/exams/{examId}/attempt/questions/{questionRealNumber}/previous")
    public String previousQuestion(@PathVariable String examId, @PathVariable String questionRealNumber,HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());

        Exam examById = examService.getExamById(examId);
        if (examById != null) {
            if (examById.getManagersGroupsSubjects().getGroupInfo().equals(userGroupInfoService.getGroupInfoByUser(user).getGroupInfo())) {
                ExamAttempt attempt = examAttemptService.getAttemptByUserAndExam(user, examById);
                if (attempt != null && request.getHeader("Referer") != null && attempt.getFinishedAt() == null) {
                   if(Integer.parseInt(questionRealNumber)>2)
                        return "redirect:/user/dashboard/exams/" + examId + "/attempt/questions/" + (Integer.parseInt(questionRealNumber) - 1);
                   else return "redirect:/user/dashboard/exams/" + examId + "/attempt/questions/" + (Integer.parseInt(questionRealNumber));
                }

            }

        }
        return "redirect:/user/dashboard";
    }

    @PostMapping("/user/dashboard/exams/{examId}/attempt/questions/{questionRealNumber}")
    public String submitAnswer(@PathVariable String examId, @PathVariable String questionRealNumber, HttpServletRequest request, @ModelAttribute("question") ExamAnswer examAnswer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());

        Exam examById = examService.getExamById(examId);
        if (examById != null) {
            if (examById.getManagersGroupsSubjects().getGroupInfo().equals(userGroupInfoService.getGroupInfoByUser(user).getGroupInfo())) {
                ExamAttempt attempt = examAttemptService.getAttemptByUserAndExam(user, examById);
                if (attempt != null && request.getHeader("Referer") != null && attempt.getFinishedAt() == null) {
                    ExamAnswer answer = examAnswerService.getExamAnswerByAttemptAndRealNumber(attempt, Integer.parseInt(questionRealNumber));
                    answer.setSelectedAnswer(examAnswer.getSelectedAnswer());
                    examAnswerService.save(answer);
                }

            }

        }
        return "redirect:/user/dashboard/exams/" + examId + "/attempt/questions/" + questionRealNumber+"/next";
    }

    @GetMapping("/user/dashboard/exams/{examId}/attempt/finish")
    public String previousQuestion(@PathVariable String examId, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        Exam examById = examService.getExamById(examId);
        if (examById != null) {
            if (examById.getManagersGroupsSubjects().getGroupInfo().equals(userGroupInfoService.getGroupInfoByUser(user).getGroupInfo())) {
                ExamAttempt attempt = examAttemptService.getAttemptByUserAndExam(user, examById);
                if (attempt != null && request.getHeader("Referer") != null && attempt.getFinishedAt() == null) {
                    attempt.setFinishedAt(LocalDateTime.now());
                    attempt.setGrade(examAttemptService.calculateExamGrade(attempt));
                    examAttemptService.save(attempt);
                    return "redirect:/user/dashboard";
                }
            }

        }
        return"redirect:/user/dashboard";
    }

}

