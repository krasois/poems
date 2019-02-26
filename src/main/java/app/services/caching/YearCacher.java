package app.services.caching;

import java.util.List;

public interface YearCacher {

    List<Integer> getYears();

    void clearAll();
}