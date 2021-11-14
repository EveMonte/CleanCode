import Planes.ExperimentalPlane;
import models.ClassificationLevel;
import models.ExperimentalTypes;
import models.MilitaryType;
import org.testng.Assert;
import org.testng.annotations.Test;
import Planes.MilitaryPlane;
import Planes.PassengerPlane;
import Planes.Plane;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AirportTest {
    private static List<Plane> planes = Arrays.asList(
            new PassengerPlane("Boeing-737", 900, 12000, 60500, 164),
            new PassengerPlane("Boeing-737-800", 940, 12300, 63870, 192),
            new PassengerPlane("Boeing-747", 980, 16100, 70500, 242),
            new PassengerPlane("Airbus A320", 930, 11800, 65500, 188),
            new PassengerPlane("Airbus A330", 990, 14800, 80500, 222),
            new PassengerPlane("Embraer 190", 870, 8100, 30800, 64),
            new PassengerPlane("Sukhoi Superjet 100", 870, 11500, 50500, 140),
            new PassengerPlane("Bombardier CS300", 920, 11000, 60700, 196),
            new MilitaryPlane("B-1B Lancer", 1050, 21000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("B-2 Spirit", 1030, 22000, 70000, MilitaryType.BOMBER),
            new MilitaryPlane("B-52 Stratofortress", 1000, 20000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("F-15", 1500, 12000, 10000, MilitaryType.FIGHTER),
            new MilitaryPlane("F-22", 1550, 13000, 11000, MilitaryType.FIGHTER),
            new MilitaryPlane("C-130 Hercules", 650, 5000, 110000, MilitaryType.TRANSPORT),
            new ExperimentalPlane("Bell X-14", 277, 482, 500, ExperimentalTypes.HIGH_ALTITUDE, ClassificationLevel.SECRET),
            new ExperimentalPlane("Ryan X-13 Vertijet", 560, 307, 500, ExperimentalTypes.VTOL, ClassificationLevel.TOP_SECRET)
    );

    private static PassengerPlane planeWithMaxPassengerCapacity = new PassengerPlane("Boeing-747", 980, 16100, 70500, 242);

    @Test
    public void AreTransportMilitaryPlanesAtTheAirport() {
        List<MilitaryPlane> transportMilitaryPlanes = (new Airport(planes)).getTransportMilitaryPlanes();
        Assert.assertNotNull(transportMilitaryPlanes.stream().filter(plane -> plane.getMilitaryType() == MilitaryType.TRANSPORT));
    }


    @Test
    public void FindPassengerPlaneWithMaxCapacity() {
        Assert.assertTrue(planeWithMaxPassengerCapacity.equals((new Airport(planes)).getPassengerPlaneWithMaxPassengersCapacity()));
    }

    @Test
    public void IsAscendingSortingByMaxLoadCapacityAtTheAirport() {
        Airport airport = new Airport(planes);
        airport.sortByMaxLoadCapacity();
        List<? extends Plane> planesSortedByMaxLoadCapacity = airport.getPlanes();
        List<? extends Plane> expectedPlanesSortedByMaxLoadCapacity = airport.getPlanes();
        expectedPlanesSortedByMaxLoadCapacity.sort(Comparator.comparing(Plane::getMaxLoadCapacity).reversed());
        Assert.assertEquals(planesSortedByMaxLoadCapacity, expectedPlanesSortedByMaxLoadCapacity);
    }

    @Test
    public void IsAtLeastOneBomberAtTheAirport() {
        List<MilitaryPlane> bomberMilitaryPlanes = new Airport(planes).getBomberMilitaryPlanes();
        Assert.assertNotNull(bomberMilitaryPlanes.stream().filter(plane -> plane.getMilitaryType() == MilitaryType.BOMBER));
    }

    @Test
    public void ExperimentalPlanesHasNotUnclassifiedClassificationLevel(){
        List<ExperimentalPlane> experimentalPlanes = (new Airport(planes)).getExperimentalPlanes();
        Assert.assertFalse(experimentalPlanes.stream().
                anyMatch(plane -> plane.getClassificationLevel() == ClassificationLevel.UNCLASSIFIED));
    }
}
