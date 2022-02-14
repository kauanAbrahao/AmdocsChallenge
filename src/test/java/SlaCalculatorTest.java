import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class SlaCalculatorTest {

    @Test
    public void calculateSlaTest(){

        SlaCalculator slaCalculator = new SlaCalculator();

        LocalDateTime inputDateTime = LocalDateTime.of(2019, 8, 1, 8, 0, 0, 0);
        System.out.println(inputDateTime);
        int iSla = 8;

        LocalDateTime slaResponse = slaCalculator.calculateSLA(inputDateTime, iSla);

        Assert.assertEquals(inputDateTime.plusHours(iSla), slaResponse);
    }


    @Test
    public void calculateSla_mustThrowException_slaJumpedToAnotherMonth(){
        SlaCalculator slaCalculator = new SlaCalculator();

        LocalDateTime inputDateTime = LocalDateTime.of(2019, 8, 31, 16, 0, 0, 0);
        System.out.println(inputDateTime);
        int iSla = 8;

        try{
            slaCalculator.calculateSLA(inputDateTime, iSla);
            Assert.fail("must throw exception");
        } catch (RuntimeException ex){
            Assert.assertTrue(true);
        }

    }

    @Test
    public void calculateSla_edgeCases_endOfBusinessDay(){
        SlaCalculator slaCalculator = new SlaCalculator();

        LocalDateTime inputDateTime = LocalDateTime.of(2019, 8, 1, 16, 0, 0, 0);
        System.out.println(inputDateTime);
        int iSla = 2;

        LocalDateTime slaResponse = slaCalculator.calculateSLA(inputDateTime, iSla);

        LocalDateTime expectedDateTime = LocalDateTime.of(2019, 8, 2, 9, 0, 0, 0);
        Assert.assertEquals(expectedDateTime, slaResponse);
    }

    @Test
    public void calculateSla_edgeCases_startOfBusinessDay(){
        SlaCalculator slaCalculator = new SlaCalculator();

        LocalDateTime inputDateTime = LocalDateTime.of(2019, 8, 1, 7, 0, 0, 0);
        System.out.println(inputDateTime);
        int iSla = 2;

        LocalDateTime slaResponse = slaCalculator.calculateSLA(inputDateTime, iSla);

        LocalDateTime expectedDateTime = LocalDateTime.of(2019, 8, 1, 10, 0, 0, 0);
        Assert.assertEquals(expectedDateTime, slaResponse);
    }

    @Test
    public void calculateSla_weekendCase(){
        SlaCalculator slaCalculator = new SlaCalculator();

        LocalDateTime inputDateTime = LocalDateTime.of(2022, 2, 11, 16, 0, 0, 0);
        System.out.println(inputDateTime);
        int iSla = 5;

        LocalDateTime slaResponse = slaCalculator.calculateSLA(inputDateTime, iSla);

        LocalDateTime expectedDateTime = LocalDateTime.of(2022, 2, 14, 12, 0, 0, 0);
        Assert.assertEquals(expectedDateTime, slaResponse);
    }

}
