package pl.put.poznan.utils.converters;


import pl.put.poznan.model.Reader;
import pl.put.poznan.modelFx.ReaderFx;

public class ReaderConverter {

    public static Reader convertToReader(ReaderFx readerFx){
        Reader reader = new Reader();
        reader.setReaderId(readerFx.getReaderId());
        reader.setFirstName(readerFx.getFirstName());
        reader.setLastName(readerFx.getLastName());
        reader.setCity(readerFx.getCity());
        reader.setStreetAndHouseNumber(readerFx.getStreetAndHouseNumber());
        reader.setIfActive(readerFx.getIfActive());
        reader.setEmailAddress(readerFx.getEmailAddress());
        return reader;
    }

    public static ReaderFx convertToReaderFx(Reader reader){
        ReaderFx readerFx = new ReaderFx();
        readerFx.setReaderId(reader.getReaderId());
        readerFx.setFirstName(reader.getFirstName());
        readerFx.setLastName(reader.getLastName());
        readerFx.setCity(reader.getCity());
        readerFx.setStreetAndHouseNumber(reader.getStreetAndHouseNumber());
        readerFx.setIfActive(reader.getIfActive());
        readerFx.setEmailAddress(reader.getEmailAddress());
        return readerFx;
    }
}
