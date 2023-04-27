package functions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.http.HttpStatus;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.core.ApiFuture;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdTokenCredentials;
import com.google.auth.oauth2.IdTokenProvider;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import functions.model.File;
import functions.model.Todo;

public class Crud implements HttpFunction {

  private static final Gson gson = new Gson();

  private static final Logger logger = Logger.getLogger(Crud.class.getName());

  public void service(final HttpRequest request, final HttpResponse response) throws Exception {
    if (cors(request, response)) {
      return;
    }


    // SET environment variables

    String projectId = getEnvVar("PROJECT_ID", true, response);
    String gcsHelperUrl = getEnvVar("HELPER_URL", true, response);
    String fileBucket = getEnvVar("FILE_BUCKET", true, response);

    // Setup connection to firestore
    FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
        .setProjectId(projectId)
        .setCredentials(GoogleCredentials.getApplicationDefault())
        .build();
    Firestore db = firestoreOptions.getService();
    CollectionReference collection = db.collection("todos");

    // Handle different HTTP methods as CRUD representation
    switch (request.getMethod()) {

      case "GET":

        // BEGIN STEP 1 - get all todos from database
        
          // END STEP 1

          
          // BEGIN STEP 3 - get all file references from a todo from the database
          
          // END STEP 3

          // BEGIN STEP 1.1
          // add all found todos to a list and return them
          todos.add(todo);

          // END STEP 1.1

        }

        writeResponse(gson.toJson(todos), true, HttpStatus.SC_OK, response);
        break;

      // CREATE - create a new todo an write it into the database
      case "POST":

        // BEGIN STEP 2
        
        break;
      // END STEP 2
      
      // UPDATE an existing todo
      case "PATCH":
        // BEGIN STEP 3
        
        break;
        // END STEP 3

      // DELETE a todo from the database
      case "DELETE":
        // BEGIN STEP 4
        
        break;
      // END STEP 4
      
      // Call gcs_helper Cloud Function to generated a signed URL which is used by the frontend to upload an file to the GCS bucket
      case "PUT":
      // BEGIN STEP 5
        
        break;
      // END STEP 5
      
      default:
        response.setStatusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
        break;
    }
  }

  /*
  ------- helper methods ------
  */

  private void writeResponse(final String content, final Boolean isJson, final int statusCode,
      final HttpResponse response)
      throws IOException {
    try {
      final BufferedWriter writer = response.getWriter();
      logger.info("Sending: " + content);
      writer.write(content);
      if (isJson) {
        response.setContentType("application/json");
      } else {
        response.setContentType("text/plain");
      }
      response.setStatusCode(statusCode);
    } catch (Exception error) {
      logger.severe("Failed to write response: " + error);
      response.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
      throw error;
    }
  }

  private <T> Object parseRequest(Class<T> C, final HttpRequest request, final HttpResponse response)
      throws IOException {
    try {
      if (!"application/json".equals(request.getContentType().orElse(""))) {
        response.setStatusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
        return null;
      }
      return gson.fromJson(request.getReader(), C);
    } catch (JsonSyntaxException error) {
      writeResponse(error.getMessage(), false, HttpStatus.SC_BAD_REQUEST, response);
      throw error;
    } catch (Exception error) {
      logger.severe("Failed to parse request: " + error);
      response.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
      throw error;
    }
  }

  private String getEnvVar(final String name, final Boolean required, final HttpResponse response) throws Exception {
    String value = System.getenv(name);
    if (required && value == null) {
      writeResponse("Invalid function configuration", false, HttpStatus.SC_INTERNAL_SERVER_ERROR, response);
      String message = "The required environment variable " + name + " is missing";
      logger.severe(message);
      throw new IllegalArgumentException(message);
    }
    return value;
  }

  private Boolean cors(final HttpRequest request, final HttpResponse response) {
    response.appendHeader("Access-Control-Allow-Origin", "*");
    response.appendHeader("Access-Control-Allow-Credentials", "true");

    if ("OPTIONS".equals(request.getMethod())) {
      response.appendHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH");
      response.appendHeader("Access-Control-Allow-Headers", "*");
      response.appendHeader("Access-Control-Max-Age", "3600");
      response.setStatusCode(HttpStatus.SC_NO_CONTENT);
      return true;
    }
    return false;
  }
}