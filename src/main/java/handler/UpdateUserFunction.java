package handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import layer.service.APIGatewayService;
import layer.service.APIGatewayServiceImpl;
import layer.service.DynamoDBService;
import layer.service.DynamoDBServiceImpl;

import java.util.Map;


public class UpdateUserFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final DynamoDBService dynamoDBService = new DynamoDBServiceImpl();
    private static final APIGatewayService apiGatewayService = new APIGatewayServiceImpl();

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
                                                      final Context context) {
        try {
            Map<String, String> pathParameters = input.getPathParameters();

            String requestBody = input.getBody();

            dynamoDBService.updateUser(pathParameters, requestBody);

            return apiGatewayService.getApiGatewayProxyResponseEvent("Update successful", 200);
        } catch (Exception e) {
            return apiGatewayService.getApiGatewayProxyResponseEvent(
                    "An error occurred while updating the record: " + e.getClass() + "; message: " + e.getMessage(),
                    503);
        }
    }
}
