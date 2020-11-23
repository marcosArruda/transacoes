package br.com.marcos.transacoes.config;

public class AWSConfigConstants {

    public static class SQS {

        public static class Queues {

            public static final String GENERAL_EVENTS = "${aws.sqs.queue.general_events}";

            private Queues() {}
        }

        public static final String URL = "${aws.sqs.url}";

        private SQS() {}
    }

    public static class MongoDB {

        public static class Events {

            public static final String URL = "${db.host}";

            public static final String PORT = "${db.port}";

            public static final String NAME = "${db.database}";

            public static final String USERNAME = "${db.username}";
            public static final String PASSWORD = "${db.password}";

            private Events() {}
        }

        private MongoDB() {}
    }

    public static final String REGION = "${aws.region}";

    private AWSConfigConstants() {}
}
