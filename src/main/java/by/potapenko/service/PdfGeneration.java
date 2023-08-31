package by.potapenko.service;

import by.potapenko.database.entity.AccountEntity;
import by.potapenko.database.entity.TransactionEntity;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.NoArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static by.potapenko.service.util.ChequeUtil.ACCOUNT;
import static by.potapenko.service.util.ChequeUtil.ACCOUNT_BALANCE;
import static by.potapenko.service.util.ChequeUtil.ACCOUNT_RECIPIENT;
import static by.potapenko.service.util.ChequeUtil.ACCOUNT_SEND;
import static by.potapenko.service.util.ChequeUtil.AMOUNT;
import static by.potapenko.service.util.ChequeUtil.BANK_RECIPIENT;
import static by.potapenko.service.util.ChequeUtil.BANK_SEND;
import static by.potapenko.service.util.ChequeUtil.CHEQUE_NUMBER;
import static by.potapenko.service.util.ChequeUtil.CHEQUE_TITLE;
import static by.potapenko.service.util.ChequeUtil.CURRENCY;
import static by.potapenko.service.util.ChequeUtil.CUSTOMER;
import static by.potapenko.service.util.ChequeUtil.DATE;
import static by.potapenko.service.util.ChequeUtil.DATE_OPEN_ACCOUNT;
import static by.potapenko.service.util.ChequeUtil.FONT_PATH;
import static by.potapenko.service.util.ChequeUtil.NOTE;
import static by.potapenko.service.util.ChequeUtil.PERIOD;
import static by.potapenko.service.util.ChequeUtil.STATEMENT_TITLE;
import static by.potapenko.service.util.ChequeUtil.TYPE_TRANSACTION;
import static javax.swing.text.StyleConstants.ALIGN_CENTER;

@NoArgsConstructor
public class PdfGeneration {
    private final PdfPTable table = new PdfPTable(2);
    private final Document document = new Document();
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private final String FONT = FONT_PATH;

    public ByteArrayOutputStream createPdfToCheque(TransactionEntity transaction) {


        try {
            BaseFont arial = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(arial, 10, Font.NORMAL);
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            addTableHeader(table, font, CHEQUE_TITLE);

            addRow(table, font, CHEQUE_NUMBER);
            addRow(table, font, transaction.getId().toString());

            addRow(table, font, transaction.getDate().toString());
            addRow(table, font, transaction.getTime().toString());

            addRow(table, font, TYPE_TRANSACTION);
            addRow(table, font, transaction.getType().getTITLE());

            addRow(table, font, BANK_SEND);
            addRow(table, font, transaction.getAccountSend().getBank().getBankName().getTitle());

            addRow(table, font, BANK_RECIPIENT);
            addRow(table, font, transaction.getAccountRecipient().getBank().getBankName().getTitle());

            addRow(table, font, ACCOUNT_SEND);
            addRow(table, font, transaction.getAccountSend().getIban());

            addRow(table, font, ACCOUNT_RECIPIENT);
            addRow(table, font, transaction.getAccountRecipient().getIban());

            addRow(table, font, AMOUNT);
            addRow(table, font, transaction.getAmount().toString() + transaction.getAccountSend().getCurrency());

            document.add(table);
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream;
    }

    public ByteArrayOutputStream createPdfToStatement(AccountEntity account, String dateFrom, String dateTo) {
        PdfPTable tableTransaction = new PdfPTable(3);
        try {
            BaseFont arial = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(arial, 10, Font.NORMAL);
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            addTableHeader(table, font, STATEMENT_TITLE);
            addTableHeader(table, font, account.getBank().getBankName().getTitle());

            addRow(table, font, CUSTOMER);
            addRow(table, font, account.getOwner().getFullName());

            addRow(table, font, ACCOUNT);
            addRow(table, font, account.getIban());

            addRow(table, font, CURRENCY);
            addRow(table, font, account.getCurrency().toString());

            addRow(table, font, DATE_OPEN_ACCOUNT);
            addRow(table, font, account.getDateOfCreation().toString());

            addRow(table, font, PERIOD);
            addRow(table, font, dateFrom + "-" + dateTo);

            addRow(table, font, ACCOUNT_BALANCE);
            addRow(table, font, account.getBalance().toString() + account.getCurrency().name());

            addRow(tableTransaction, font, DATE);
            addRow(tableTransaction, font, NOTE);
            addRow(tableTransaction, font, AMOUNT);

            addRowTransaction(account.getTransactions(), tableTransaction, font);

            document.add(table);
            document.add(tableTransaction);
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream;
    }

    private static void addTableHeader(PdfPTable table, Font font, String title) {
        PdfPCell header = new PdfPCell();
        header.setColspan(2);
        header.setHorizontalAlignment(ALIGN_CENTER);
        header.setBorder(0);
        header.setPhrase(new Phrase(title, font));
        table.addCell(header);
    }

    private static void addRow(PdfPTable table, Font font, String title) {
        PdfPCell cell = new PdfPCell(new Phrase(title, font));
        cell.setBorder(0);
        table.addCell(cell);
    }

    private static void addRowTransaction(List<TransactionEntity> transaction, PdfPTable table, Font font) {
        for (TransactionEntity transactionEntity : transaction) {
            addRow(table, font, transactionEntity.getDate().toString());
            addRow(table, font, transactionEntity.getType().getTITLE());
            addRow(table, font, transactionEntity.getAmount().toString());
        }
    }
}

