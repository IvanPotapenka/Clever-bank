package by.potapenko.service;

import by.potapenko.database.enam.TypeAccount;
import jakarta.servlet.ServletContext;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;
import java.util.TimerTask;

import static by.potapenko.service.util.ChequeUtil.INTEREST_PATH;

public class InterestCalculation extends TimerTask {
    private final AccountService accountService = AccountService.getInstance();
    private final InputStream inputStream;

    {
        try {
            inputStream = new FileInputStream(INTEREST_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private final Map<String, String> data = new Yaml().load(inputStream);

    private boolean isRunning = true;

    public InterestCalculation(ServletContext servletContext) {
    }

    private Double calculateSumOfInterest(Double currentBalance) {
        var rate = Double.parseDouble(data.get("interest_rate"));
        var month = Double.parseDouble(data.get("days_of_month"));
        var year = Double.parseDouble(data.get("days_of_year"));
        var interest = currentBalance * rate / month * year;
        var round = new BigDecimal(String.valueOf(interest));
        return Double.valueOf(round.setScale(2, RoundingMode.HALF_DOWN).toString());
    }

    @Override
    public void run() {
        LocalDate ld = LocalDate.now();
        if (ld.isEqual(ld.withDayOfMonth(ld.lengthOfMonth()))) {
            if (isRunning) {
                isRunning = false;
                accountService.findAll().forEach(account -> {
                    if (account.getAccount().equals(TypeAccount.DEPOSIT)) {
                        Double currentBalance = account.getBalance();
                        Double interestAmount = calculateSumOfInterest(currentBalance);
                        account.setBalance(currentBalance + interestAmount);
                        account.setInterestAmount(interestAmount);
                        accountService.update(account);
                    }
                });
            }
        } else {
            isRunning = true;
        }
    }
}





