package fr.cytech.projetjeejakarta.model;

import fr.cytech.projetjeejakarta.dao.FicheDePaieDAO;
import fr.cytech.projetjeejakarta.dao.HistoriquePaieDAO;
import jakarta.servlet.ServletContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

import java.time.LocalDate;

public class MonthlyTaskJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        ServletContext servletContext = null;

        try {
            servletContext = (ServletContext)
                    context.getScheduler().getContext().get("servletContext");
        } catch (SchedulerException e) {
            throw new RuntimeException("Cannot access scheduler context", e);
        }

        LocalDate target = LocalDate.now();
        int year = target.getYear();
        int month = target.getMonthValue();


        HistoriquePaieDAO histDAO = new HistoriquePaieDAO();


        if (histDAO.existsSuccessForMonth(year, month)){
            System.out.println("Payslips already generated for " + year + "-" + month);
            return;
        }

        boolean success = false;

        try {
            FicheDePaieDAO ficheDAO = new FicheDePaieDAO();
            ficheDAO.generateMonthlyFiches();

            success = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            histDAO.saveRun(success);
        }
    }
}
