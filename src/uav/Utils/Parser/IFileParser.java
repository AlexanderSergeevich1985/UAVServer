package TestTask;

import java.util.List;

public interface IFileParser {
    List readFromFile(String filePath);

    void setDataScheme(DataScheme dataScheme);

    DataScheme getDataScheme();
}