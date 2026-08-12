package exam;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ExamServlet")
public class ExamServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Get student name
        String studentName =
                request.getParameter("studentName");

        // Get answers
        String q1 = request.getParameter("q1");
        String q2 = request.getParameter("q2");
        String q3 = request.getParameter("q3");
        String q4 = request.getParameter("q4");
        String q5 = request.getParameter("q5");
        String q6 = request.getParameter("q6");
        String q7 = request.getParameter("q7");
        String q8 = request.getParameter("q8");
        String q9 = request.getParameter("q9");
        String q10 = request.getParameter("q10");

        // Calculate score
        int score = 0;

        if ("B".equals(q1)) score++;
        if ("C".equals(q2)) score++;
        if ("A".equals(q3)) score++;
        if ("B".equals(q4)) score++;
        if ("A".equals(q5)) score++;
        if ("C".equals(q6)) score++;
        if ("B".equals(q7)) score++;
        if ("B".equals(q8)) score++;
        if ("A".equals(q9)) score++;
        if ("C".equals(q10)) score++;

        // Percentage
        double percentage =
                (score / 10.0) * 100;

        // Grade
        String grade;

        if (score >= 9) {

            grade = "A+";

        } else if (score >= 8) {

            grade = "A";

        } else if (score >= 7) {

            grade = "B";

        } else if (score >= 5) {

            grade = "C";

        } else {

            grade = "D";
        }

        // Save result into MySQL
        saveResult(
                studentName,
                score,
                percentage,
                grade
        );

        // Display result
        response.setContentType("text/html");

        PrintWriter out =
                response.getWriter();

        out.println("<!DOCTYPE html>");

        out.println("<html>");
        out.println("<head>");

        out.println("<title>Exam Result</title>");

        out.println("<style>");

        out.println(
            "body{" +
            "margin:0;" +
            "font-family:Arial;" +
            "background:linear-gradient(135deg,#4f46e5,#7c3aed);" +
            "display:flex;" +
            "justify-content:center;" +
            "align-items:center;" +
            "min-height:100vh;" +
            "}"
        );

        out.println(
            ".card{" +
            "background:white;" +
            "width:90%;" +
            "max-width:550px;" +
            "padding:40px;" +
            "border-radius:25px;" +
            "text-align:center;" +
            "box-shadow:0 20px 50px rgba(0,0,0,.25);" +
            "}"
        );

        out.println(
            ".icon{" +
            "font-size:60px;" +
            "}"
        );

        out.println(
            "h1{" +
            "color:#312e81;" +
            "}"
        );

        out.println(
            ".score{" +
            "font-size:55px;" +
            "font-weight:bold;" +
            "color:#6366f1;" +
            "margin:20px;" +
            "}"
        );

        out.println(
            ".box{" +
            "background:#f5f3ff;" +
            "padding:20px;" +
            "border-radius:15px;" +
            "margin:15px 0;" +
            "}"
        );

        out.println(
            ".btn{" +
            "display:inline-block;" +
            "padding:14px 30px;" +
            "background:#4f46e5;" +
            "color:white;" +
            "text-decoration:none;" +
            "border-radius:30px;" +
            "margin-top:20px;" +
            "}"
        );

        out.println("</style>");

        out.println("</head>");

        out.println("<body>");

        out.println("<div class='card'>");

        out.println(
                "<div class='icon'>🏆</div>"
        );

        out.println(
                "<h1>Examination Completed!</h1>"
        );

        out.println(
                "<p>Well done, <b>"
                + studentName +
                "</b>!</p>"
        );

        out.println(
                "<div class='score'>"
                + score +
                " / 10</div>"
        );

        out.println(
                "<div class='box'>" +
                "<b>Percentage:</b> "
                + percentage +
                "%</div>"
        );

        out.println(
                "<div class='box'>" +
                "<b>Grade:</b> "
                + grade +
                "</div>"
        );

        out.println(
                "<a class='btn' href='index.html'>" +
                "Take Exam Again 🔄" +
                "</a>"
        );

        out.println("</div>");

        out.println("</body>");

        out.println("</html>");
    }


    // DATABASE FUNCTION

    private void saveResult(
            String studentName,
            int score,
            double percentage,
            String grade) {

        String sql =
            "INSERT INTO results " +
            "(student_name, score, percentage, grade) " +
            "VALUES (?, ?, ?, ?)";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, studentName);

            ps.setInt(2, score);

            ps.setDouble(3, percentage);

            ps.setString(4, grade);

            ps.executeUpdate();

            ps.close();

            con.close();

            System.out.println(
                "Result Saved Successfully!"
            );

        } catch (Exception e) {

            System.out.println(
                "Result Saving Failed!"
            );

            e.printStackTrace();
        }
    }
}