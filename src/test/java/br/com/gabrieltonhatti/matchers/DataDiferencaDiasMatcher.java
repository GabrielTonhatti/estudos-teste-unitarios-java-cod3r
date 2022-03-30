package br.com.gabrieltonhatti.matchers;

import br.com.gabrieltonhatti.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Date;

import static br.com.gabrieltonhatti.utils.DataUtils.obterDataComDiferencaDias;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date> {

    private Integer qtdeDias;

    public DataDiferencaDiasMatcher(Integer qtdeDias) {
        this.qtdeDias = qtdeDias;
    }

    @Override
    protected boolean matchesSafely(Date data) {
        return DataUtils.isMesmaData(data, obterDataComDiferencaDias(qtdeDias));
    }

    @Override
    public void describeTo(Description description) {

    }

}
