package by.potapenko.service;

import by.potapenko.database.entity.AccountEntity;
import by.potapenko.database.entity.TransactionEntity;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static by.potapenko.service.util.ChequeUtil.CHEQUE_FILE_PATH;
import static by.potapenko.service.util.ChequeUtil.STATEMENT_FILE_PATH;
import static by.potapenko.service.util.ChequeUtil.SUFFIX;

public class OutPutService {
    public static void writeChequePdfFile(TransactionEntity transaction) {
        PdfGeneration pdf = new PdfGeneration();
        ByteArrayOutputStream byteArrayOutputStream = pdf.createPdfToCheque(transaction);
        try (
                OutputStream outputStream = new FileOutputStream((CHEQUE_FILE_PATH + transaction.getId().toString() + SUFFIX))) {
            byteArrayOutputStream.writeTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeStatementPdfFile(AccountEntity account, String daneFrom, String dateTo) {
        PdfGeneration pdf = new PdfGeneration();
        ByteArrayOutputStream byteArrayOutputStream = pdf.createPdfToStatement(account, daneFrom, dateTo);
        try (
                OutputStream outputStream = new FileOutputStream((STATEMENT_FILE_PATH + account.getIban() + SUFFIX))) {
            byteArrayOutputStream.writeTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
