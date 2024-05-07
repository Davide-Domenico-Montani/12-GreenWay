package it.unimib.greenway.model;

public class Result {
    private Result(){}

    public boolean isSuccessUser() {
        return this instanceof UserResponseSuccess;
    }

    public boolean isSuccessAirQuality() {
        return this instanceof AirQualityResponseSuccess;
    }
    public boolean isSuccessChallenge() {
        return this instanceof ChallengeResponseSuccess;
    }


    public boolean isSuccessRoutes(){
        return this instanceof RouteResponseSuccess;
    }
    public static final class UserResponseSuccess extends Result {
        private final User user;
        public UserResponseSuccess(User user) {
            this.user = user;
        }
        public User getData() {
            return user;
        }
    }


    public static final class AirQualityResponseSuccess extends Result {
        private final AirQualityResponse airQuality;
        public AirQualityResponseSuccess(AirQualityResponse airQuality) {
            this.airQuality = airQuality;
        }
        public AirQualityResponse getData() {
            return airQuality;
        }
    }

    public static final class RouteResponseSuccess extends Result {
        private final RoutesResponse routesResponse;
        public RouteResponseSuccess(RoutesResponse routeResponse) {
            this.routesResponse = routeResponse;
        }
        public RoutesResponse getData() {
            return routesResponse;
        }
    }

    public static final class ChallengeResponseSuccess extends Result {
        private final ChallengeResponse challengeResponse;
        public ChallengeResponseSuccess(ChallengeResponse challengeResponse) {
            this.challengeResponse = challengeResponse;
        }
        public ChallengeResponse getData() {
            return challengeResponse;
        }
    }
    public static final class Error extends Result {
        private final String message;
        public Error(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }


}
