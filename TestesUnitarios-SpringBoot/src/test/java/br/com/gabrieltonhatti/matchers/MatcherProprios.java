package br.com.gabrieltonhatti.matchers;

import java.util.Calendar;

public class MatcherProprios {

    public static DiaSemanaMatcher caiEm(Integer diaSeamana) {
        return new DiaSemanaMatcher(diaSeamana);
    }

    public static DiaSemanaMatcher caiNumaSegunda() {
        return new DiaSemanaMatcher(Calendar.MONDAY);
    }

    public static DataDiferencaDiasMatcher ehHojeComDiferencaDias(Integer qtdeDias) {
        return new DataDiferencaDiasMatcher(qtdeDias);
    }

    public static DataDiferencaDiasMatcher ehHoje() {
        return ehHojeComDiferencaDias(0);
    }

}
