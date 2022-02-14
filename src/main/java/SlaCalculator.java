import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

public class SlaCalculator {

    private static final int INIT_BUSINESS_HOUR = 8;
    private static final int END_BUSINESS_HOUR = 17;

    public LocalDateTime calculateSLA(LocalDateTime iOpeningDateTime, Integer iSLA){

        LocalDateTime slaResponse = iOpeningDateTime;
        Month monthOpeningDate = iOpeningDateTime.getMonth();

        slaResponse = calculateTimeForWeekends(slaResponse);
        slaResponse = calculateSlaBusinessHour(slaResponse, iSLA);

        if(monthOpeningDate != slaResponse.getMonth()){
            throw new RuntimeException("Request not accepted - SLA extrapolated to next month");
        }

        return slaResponse;
    }


    private LocalDateTime calculateSlaBusinessHour(LocalDateTime slaResponse, Integer iSLA) {
        for(int hourCont = 0; hourCont < iSLA; hourCont++){
            slaResponse = slaResponse.plusHours(1);

            if(!isBusinessHour(slaResponse.getHour())){
                hourCont--;
            }
        }
        return slaResponse;
    }

    private LocalDateTime calculateTimeForWeekends(LocalDateTime slaResponse) {

        while (slaResponse.getDayOfWeek() == DayOfWeek.SATURDAY || slaResponse.getDayOfWeek() == DayOfWeek.SUNDAY) {
            slaResponse = slaResponse.plusHours(1);
        }

        return slaResponse;
    }

    private boolean isBusinessHour(int iOpeningHour) {

        if(iOpeningHour <= INIT_BUSINESS_HOUR || iOpeningHour > END_BUSINESS_HOUR){
            return false;
        }

        return true;
    }
}
