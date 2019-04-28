package pl.put.poznan.utils.converters;

import pl.put.poznan.model.Loan;
import pl.put.poznan.model.LoanPk;
import pl.put.poznan.modelFx.LoanFx;

import static pl.put.poznan.utils.converters.BookCopyConverter.convertToBookCopy;
import static pl.put.poznan.utils.converters.BookCopyConverter.convertToBookCopyFx;
import static pl.put.poznan.utils.converters.LibrarianConverter.convertToLibrarian;
import static pl.put.poznan.utils.converters.LibrarianConverter.convertToLibrarianFx;
import static pl.put.poznan.utils.converters.ReaderConverter.convertToReader;
import static pl.put.poznan.utils.converters.ReaderConverter.convertToReaderFx;

public class LoanConverter {

    public static Loan convertToLoan(LoanFx loanFx){
        Loan loan = new Loan();
        loan.setIfReturn(loanFx.getIfReturn());
        loan.setReturnDate(loanFx.getReturnDate());
        loan.setBookCopy(convertToBookCopy(loanFx.getBookCopyFx()));
        loan.setLibrarian(convertToLibrarian(loanFx.getLibrarianFx()));
        loan.setReader(convertToReader(loanFx.getReaderFx()));
        loan.setLoanPk(new LoanPk(loanFx.getRentalDate(),loanFx.getLibrarianId(),loanFx.getBookCopyId(),loanFx.getReaderId()));
        return loan;
    }

    public static LoanFx convertToLoanFx(Loan loan){
        LoanFx loanFx = new LoanFx();
        loanFx.setIfReturn(loan.getIfReturn());
        loanFx.setReturnDate(loan.getReturnDate());
        loanFx.setLibrarianId(loan.getLoanPk().getLibrarianId());
        loanFx.setReaderId(loan.getLoanPk().getReaderId());
        loanFx.setRentalDate(loan.getLoanPk().getRentalDate());
        loanFx.setBookCopyId(loan.getLoanPk().getBookCopyId());
        loanFx.setBookCopyFx(convertToBookCopyFx(loan.getBookCopy()));
        loanFx.setLibrarianFx(convertToLibrarianFx(loan.getLibrarian()));
        loanFx.setReaderFx(convertToReaderFx(loan.getReader()));
        return loanFx;
    }
}
