package it.unimib.greenway;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_DIESEL;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_ELETTRIC;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_GASOLINE;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_GPL;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_METHANE;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.hamcrest.Condition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import it.unimib.greenway.data.repository.challenge.ChallengeRepositoryWithLiveData;
import it.unimib.greenway.model.Legs;
import it.unimib.greenway.model.Route;
import it.unimib.greenway.model.Steps;
import it.unimib.greenway.model.TransitDetails;
import it.unimib.greenway.model.TransitLine;
import it.unimib.greenway.model.Vehicle;
import it.unimib.greenway.ui.main.ChallengeViewModel;
import it.unimib.greenway.util.Constants;
import it.unimib.greenway.util.ConverterUtil;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class ConverterUtilTest {
    ConverterUtil instance;
    @Before
    public void setUp() {

        ShadowLog.stream = System.out; // Per vedere i log di Robolectric nel terminale
        Application application = mock(Application.class);
        // Istanza della classe che contiene il metodo co2Converter
         instance = new ConverterUtil();
    }

    @Test
    public void testConvertSecond_lessThanAnHour() {
        String result = instance.convertSecond(1800); // 30 minuti
        assertEquals("30m ", result);
    }

    @Test
    public void testConvertSecond_oneHour() {
        String result = instance.convertSecond(3600); // 1 ora
        assertEquals("1h 0m", result);
    }

    @Test
    public void testConvertSecond_hoursAndMinutes() {

        String result = instance.convertSecond(3660); // 1 ora e 1 minuto
        assertEquals("1h 1m", result);
    }

    @Test
    public void testConvertSecond_moreThanADay() {
        String result = instance.convertSecond(90000); // 1 giorno, 1 ora e 0 minuti
        assertEquals("1d 1h 0m", result);
    }

    @Test
    public void testConvertSecond_daysHoursAndMinutes() {
        String result = instance.convertSecond(176400); // 2 giorni, 1 ora e 0 minuti
        assertEquals("2d 1h 0m", result);
    }

    @Test
    public void testConvertMeter() {
        double result = instance.convertMeter(1000); // 1000 metri
        assertEquals(1.0, result, 0.0001); // Confronta con una tolleranza per i numeri in virgola mobile
    }

    @Test
    public void testConvertMeter_largeNumber() {
        double result = instance.convertMeter(123456); // 123456 metri
        assertEquals(123.456, result, 0.0001); // Confronta con una tolleranza per i numeri in virgola mobile
    }

    @Test
    public void testCo2Calculator_drive() {
        // Mock della Route per il test DRIVE
        Route mockRoute = mock(Route.class);
        when(mockRoute.getTravelMode()).thenReturn(Constants.DRIVE_CONSTANT);
        when(mockRoute.getDistanceMeters()).thenReturn((int) 10000.0); // 10 km


        double co2Car = 0.2;
        String result = instance.co2Calculator(mockRoute, co2Car);

        // Verifica del risultato
        String expected = instance.co2Converter(10.0 * instance.co2CarEngineProduction(co2Car));
        assertEquals(expected, result);
    }

    @Test
    public void testCo2Calculator_transit() {
        // Mock della Route per il test TRANSIT
        Route mockRoute = mock(Route.class);
        when(mockRoute.getTravelMode()).thenReturn(Constants.TRANSIT_CONSTANT);

        // Mock delle Legs e Steps
        Steps step1 = mock(Steps.class);
        Steps step2 = mock(Steps.class);
        TransitDetails transitDetailsBus = mock(TransitDetails.class);
        TransitLine transitLineBus = mock(TransitLine.class);
        Vehicle vehicleBus = mock(Vehicle.class);
        when(vehicleBus.getType()).thenReturn("BUS");
        when(transitLineBus.getVehicle()).thenReturn(vehicleBus);
        when(transitDetailsBus.getTransitLine()).thenReturn(transitLineBus);
        when(step1.getTransitDetails()).thenReturn(transitDetailsBus);
        when(step1.getDistanceMeters()).thenReturn((int) 5000.0); // 5 km

        Legs leg1 = mock(Legs.class);
        when(leg1.getSteps()).thenReturn(Arrays.asList(step1, step2));

        when(mockRoute.getLegs()).thenReturn(Arrays.asList(leg1));


        String result = instance.co2Calculator(mockRoute, 0);

        // Verifica del risultato
        double expectedCO2 = Constants.CO2_PRODUCTION_BUS * 5.0; // 5 km * emissioni del bus per km
        String expected = instance.co2Converter(expectedCO2);
        assertEquals(expected, result);
    }

    @Test
    public void testCo2Calculator_walk() {
        // Mock della Route per il test WALK
        Route mockRoute = mock(Route.class);
        when(mockRoute.getTravelMode()).thenReturn(Constants.WALK_CONSTANT);


        String result = instance.co2Calculator(mockRoute, 0);

        // Verifica del risultato
        assertEquals("0kg", result);
    }

    @Test
    public void testCo2Calculator_transit_noDetails() {
        // Mock della Route per il test TRANSIT senza transitDetails
        Route mockRoute = mock(Route.class);
        when(mockRoute.getTravelMode()).thenReturn(Constants.TRANSIT_CONSTANT);

        // Mock delle Legs e Steps
        Steps step1 = mock(Steps.class);
        Steps step2 = mock(Steps.class);
        when(step1.getTransitDetails()).thenReturn(null); // Nessun dettaglio di transito
        when(step1.getDistanceMeters()).thenReturn((int) 5000.0); // 5 km

        Legs leg1 = mock(Legs.class);
        when(leg1.getSteps()).thenReturn(Arrays.asList(step1, step2));

        when(mockRoute.getLegs()).thenReturn(Arrays.asList(leg1));


        String result = instance.co2Calculator(mockRoute, 0);

        // Verifica del risultato
        double expectedCO2 = 0.0; // Nessuna emissione poiché non ci sono dettagli di transito
        String expected = instance.co2Converter(expectedCO2);
        assertEquals(expected, result);
    }


    @Test
    public void testCo2Converter() {

        // Test 1: Verifica la conversione con un valore intero
        double input1 = 1000;
        String expectedOutput1 = "1.000kg";
        String actualOutput1 = instance.co2Converter(input1);
        assertEquals(expectedOutput1, actualOutput1);

        // Test 2: Verifica la conversione con un valore decimale
        double input2 = 2500.5;
        String expectedOutput2 = "2.501kg";
        String actualOutput2 = instance.co2Converter(input2);
        assertEquals(expectedOutput2, actualOutput2);

        // Test 3: Verifica la conversione con un valore molto piccolo
        double input3 = 0.5;
        String expectedOutput3 = "0.001kg";
        String actualOutput3 = instance.co2Converter(input3);
        assertEquals(expectedOutput3, actualOutput3);

        // Test 4: Verifica la conversione con un valore molto grande
        double input4 = 1000000;
        String expectedOutput4 = "1000.000kg";
        String actualOutput4 = instance.co2Converter(input4);
        assertEquals(expectedOutput4, actualOutput4);

        // Test 5: Verifica la conversione con zero
        double input5 = 0;
        String expectedOutput5 = "0.000kg";
        String actualOutput5 = instance.co2Converter(input5);
        assertEquals(expectedOutput5, actualOutput5);

        // Test 6: Verifica la conversione con un valore negativo
        double input6 = -1000;
        String expectedOutput6 = "-1.000kg";
        String actualOutput6 = instance.co2Converter(input6);
        assertEquals(expectedOutput6, actualOutput6);
    }

    @Test
    public void testCo2SavedProgressBar() {
        // Esempi di valori di input
        double co2 = 50.0;
        double co2SavedCar = 100.0;
        double co2SavedTransit = 50.0;
        double co2SavedWalk = 50.0;

        // Calcolo atteso
        int expectedProgress = (int) (co2 * (100 / (co2SavedCar + co2SavedTransit + co2SavedWalk)));

        // Esegui il metodo e confronta il risultato atteso
        assertEquals(expectedProgress, instance.co2SavedProgressBar(co2, co2SavedCar, co2SavedTransit, co2SavedWalk));

        // Test con zero come valori di risparmio di CO2
        co2 = 0.0;
        co2SavedCar = 100.0;
        co2SavedTransit = 50.0;
        co2SavedWalk = 50.0;
        expectedProgress = (int) (co2 * (100 / (co2SavedCar + co2SavedTransit + co2SavedWalk)));
        assertEquals(expectedProgress, instance.co2SavedProgressBar(co2, co2SavedCar, co2SavedTransit, co2SavedWalk));

        // Test con co2SavedCar zero
        co2 = 50.0;
        co2SavedCar = 0.0;
        co2SavedTransit = 50.0;
        co2SavedWalk = 50.0;
        expectedProgress = (int) (co2 * (100 / (co2SavedTransit + co2SavedWalk)));
        assertEquals(expectedProgress, instance.co2SavedProgressBar(co2, co2SavedCar, co2SavedTransit, co2SavedWalk));

        // Test con tutti i valori zero
        co2 = 0.0;
        co2SavedCar = 0.0;
        co2SavedTransit = 0.0;
        co2SavedWalk = 0.0;
        expectedProgress = 0;  // Dato che tutti i valori sono zero, il risultato deve essere zero
        assertEquals(expectedProgress, instance.co2SavedProgressBar(co2, co2SavedCar, co2SavedTransit, co2SavedWalk));

        // Test con un solo valore di risparmio di CO2
        co2 = 50.0;
        co2SavedCar = 100.0;
        co2SavedTransit = 0.0;
        co2SavedWalk = 0.0;
        expectedProgress = (int) (co2 * (100 / co2SavedCar));
        assertEquals(expectedProgress, instance.co2SavedProgressBar(co2, co2SavedCar, co2SavedTransit, co2SavedWalk));
    }

    @Test
    public void testCo2CarEngineProduction() {
        // Test case per CO2 pari a 0
        assertEquals(0.0, instance.co2CarEngineProduction(0), 0.01);

        // Test case per CO2 di un'auto a benzina
        assertEquals(CO2_PRODUCTION_CAR_GASOLINE, instance.co2CarEngineProduction(-1), 0.01);

        // Test case per CO2 di un'auto a gasolio
        assertEquals(CO2_PRODUCTION_CAR_DIESEL, instance.co2CarEngineProduction(-2), 0.01);

        // Test case per CO2 di un'auto a GPL
        assertEquals(CO2_PRODUCTION_CAR_GPL, instance.co2CarEngineProduction(-3), 0.01);

        // Test case per CO2 di un'auto a metano
        assertEquals(CO2_PRODUCTION_CAR_METHANE, instance.co2CarEngineProduction(-4), 0.01);

        // Test case per CO2 di un'auto elettrica
        assertEquals(CO2_PRODUCTION_CAR_ELETTRIC, instance.co2CarEngineProduction(-5), 0.01);

        // Test case generico per un valore arbitrario di CO2
        assertEquals(150.0, instance.co2CarEngineProduction(150.0), 0.01);
    }

    @Test
    public void testBitmapToString() {
        // Creare un bitmap di esempio
        Bitmap originalBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);

        // Convertire il bitmap in una stringa Base64
        String encodedString = instance.bitmapToString(originalBitmap);

        // Convertire la stringa Base64 di nuovo in un bitmap
        byte[] decodedBytes = Base64.decode(encodedString, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

        // Confrontare il bitmap originale con il bitmap decodificato
        assertEquals(originalBitmap.getWidth(), decodedBitmap.getWidth());
        assertEquals(originalBitmap.getHeight(), decodedBitmap.getHeight());
        assertEquals(originalBitmap.getConfig(), decodedBitmap.getConfig());
    }

    @Test
    public void testStringToBitmap() {
        // Creare un bitmap di esempio
        Bitmap originalBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);

        // Convertire il bitmap in una stringa Base64
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        originalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        // Convertire la stringa Base64 di nuovo in un bitmap
        Bitmap decodedBitmap = instance.stringToBitmap(encodedString);

        // Confrontare il bitmap originale con il bitmap decodificato
        assertEquals(originalBitmap.getWidth(), decodedBitmap.getWidth());
        assertEquals(originalBitmap.getHeight(), decodedBitmap.getHeight());
        assertEquals(originalBitmap.getConfig(), decodedBitmap.getConfig());
    }

}
