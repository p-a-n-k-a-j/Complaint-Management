package com.sendresponse;

public class ResponseMessage {
	//gson use reflection that directly convert into json
        private final String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    
}
