import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

public class SlaCalculator {

    private static final int INIT_BUSINESS_HOUR = 8;
    private static final int END_BUSINESS_HOUR = 17;

    public LocalDateTime calculateSLA(LocalDateTime iOpeningDateTime, Integer iSLA){

        LocalDateTime slaResponse = iOpeningDateTime;
        Month monthOpeningDate = iOpeningDateTime.getMonth();

        slaResponse = calculateSlaBusinessHour(slaResponse, iSLA);

        if(monthOpeningDate != slaResponse.getMonth()){
            throw new RuntimeException("Request not accepted - SLA extrapolated to next month");
        }

        return slaResponse;
    }


    private LocalDateTime calculateSlaBusinessHour(LocalDateTime slaResponse, Integer iSLA) {
        for(int hourCont = 0; hourCont < iSLA; hourCont++){
            slaResponse = slaResponse.plusHours(1);

            if(!isBusinessHour(slaResponse.getHour()) || isWeeknd(slaResponse) ){
                hourCont--;
            }
        }
        return slaResponse;
    }

    private boolean isWeeknd(LocalDateTime slaResponse) {

        return slaResponse.getDayOfWeek() == DayOfWeek.SATURDAY || slaResponse.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private boolean isBusinessHour(int iOpeningHour) {

        if(iOpeningHour <= INIT_BUSINESS_HOUR || iOpeningHour > END_BUSINESS_HOUR){
            return false;
        }

        return true;
    }
}
