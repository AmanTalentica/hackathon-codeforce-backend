package com.employee.selfcare.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.employee.selfcare.entites.ChatGPTRequest;
import com.employee.selfcare.entites.ChatGptResponse;

@Service
public class GptService {

    @Value("${openai.api.url}")
    private String apiURL;

    @Value("${openai.api.key}")
    private String apiKey;
    
    @Value("${openai.model}")
    private String model;

    private final RestTemplate template;

    public GptService(RestTemplate template) {
        this.template = template;
    }

    public String generateSQLQuery(String searchText) {
        String prompt = "Given the following SQL tables, your job is to write queries with search text given:\n\n"
                + " CREATE TABLE `employee` (\r\n"
                + "  `emp_id` bigint NOT NULL AUTO_INCREMENT,\r\n"
                + "  `current_designation` varchar(255) DEFAULT NULL,\r\n"
                + "  `gender` varchar(255) DEFAULT NULL,\r\n"
                + "  `intrest_area` varchar(255) DEFAULT NULL,\r\n"
                + "  `name` varchar(255) DEFAULT NULL,\r\n"
                + "  `tools` varchar(255) DEFAULT NULL,\r\n"
                + "  `years_experience` int NOT NULL,\r\n"
                + "  `interest_area` varchar(255) DEFAULT NULL,\r\n"
                + "  PRIMARY KEY (`emp_id`)\r\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\r\n"
                + "\r\n"
                + "CREATE TABLE `employee_auth` (\r\n"
                + "  `emp_id` bigint NOT NULL AUTO_INCREMENT,\r\n"
                + "  `email` varchar(255) DEFAULT NULL,\r\n"
                + "  `password` varchar(255) DEFAULT NULL,\r\n"
                + "  PRIMARY KEY (`emp_id`)\r\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\r\n"
                + "\r\n"
                + "CREATE TABLE `employee_project` (\r\n"
                + "  `id` bigint NOT NULL AUTO_INCREMENT,\r\n"
                + "  `end_date` date DEFAULT NULL,\r\n"
                + "  `roles` varchar(255) DEFAULT NULL,\r\n"
                + "  `start_date` date DEFAULT NULL,\r\n"
                + "  `tools_used` varchar(255) DEFAULT NULL,\r\n"
                + "  `employee_emp_id` bigint DEFAULT NULL,\r\n"
                + "  `project_project_id` bigint DEFAULT NULL,\r\n"
                + "  PRIMARY KEY (`id`),\r\n"
                + "  KEY `FK39nv6hv5c519pj6mtlite41yq` (`employee_emp_id`),\r\n"
                + "  KEY `FKco0q8nmiq6dg0ahaktk1oxupu` (`project_project_id`),\r\n"
                + "  CONSTRAINT `FK39nv6hv5c519pj6mtlite41yq` FOREIGN KEY (`employee_emp_id`) REFERENCES `employee` (`emp_id`),\r\n"
                + "  CONSTRAINT `FKco0q8nmiq6dg0ahaktk1oxupu` FOREIGN KEY (`project_project_id`) REFERENCES `project` (`project_id`)\r\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\r\n"
                + "\r\n"
                + "CREATE TABLE `leave_applied` (\r\n"
                + "  `id` bigint NOT NULL AUTO_INCREMENT,\r\n"
                + "  `created_at` tinyblob,\r\n"
                + "  `end_date` datetime DEFAULT NULL,\r\n"
                + "  `reason` varchar(255) DEFAULT NULL,\r\n"
                + "  `start_date` datetime DEFAULT NULL,\r\n"
                + "  `status` varchar(255) DEFAULT NULL,\r\n"
                + "  `updated_at` tinyblob,\r\n"
                + "  `employee_id` bigint DEFAULT NULL,\r\n"
                + "  PRIMARY KEY (`id`),\r\n"
                + "  KEY `FKrauqo1er4wfw046wa556ljwr6` (`employee_id`),\r\n"
                + "  CONSTRAINT `FKrauqo1er4wfw046wa556ljwr6` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`emp_id`)\r\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\r\n"
                + "\r\n"
                + "CREATE TABLE `leave_balance` (\r\n"
                + "  `id` bigint NOT NULL AUTO_INCREMENT,\r\n"
                + "  `remaining_leaves` int NOT NULL,\r\n"
                + "  `employee_id` bigint DEFAULT NULL,\r\n"
                + "  PRIMARY KEY (`id`),\r\n"
                + "  KEY `FK9ueylmeksoyp2jvtdiovselp7` (`employee_id`),\r\n"
                + "  CONSTRAINT `FK9ueylmeksoyp2jvtdiovselp7` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`emp_id`)\r\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\r\n"
                + "\r\n"
                + "CREATE TABLE `project` (\r\n"
                + "  `project_id` bigint NOT NULL AUTO_INCREMENT,\r\n"
                + "  `description` varchar(255) DEFAULT NULL,\r\n"
                + "  `project_name` varchar(255) DEFAULT NULL,\r\n"
                + "  PRIMARY KEY (`project_id`)\r\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\r\n"
                + "\r\n"
                + "CREATE TABLE `projects` (\r\n"
                + "  `id` bigint NOT NULL AUTO_INCREMENT,\r\n"
                + "  `project_name` varchar(255) NOT NULL,\r\n"
                + "  `employee_id` bigint DEFAULT NULL,\r\n"
                + "  PRIMARY KEY (`id`),\r\n"
                + "  KEY `FKptwbppflqf850fkpj4sbyxiks` (`employee_id`),\r\n"
                + "  CONSTRAINT `FKptwbppflqf850fkpj4sbyxiks` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`emp_id`)\r\n"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\r\n"
                + " "
                + "and search Text = " + searchText;

        try {
           
            ChatGPTRequest request=new ChatGPTRequest(model, prompt);
            com.employee.selfcare.entites.ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
            String content =  chatGptResponse.getChoices().get(0).getMessage().getContent();
            String generatedQuery = extractSQLQueryFromContent(content);

            // You may need to do additional processing or validation of the generatedQuery

            String response = handleGptResponse(generatedQuery);
            return response;
           // return generatedQuery;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate SQL query with GPT-3 API.");
        }
    }

    private String extractSQLQueryFromContent(String content) {
        // Define regular expressions to match different possible formats of the SQL query
        String regex1 = "```sql\\s*\\n(.+?)\\s*```"; // Match SQL query enclosed in backticks
        String regex4 = "```sql\\r\\n\\s*\\n(.+?)\\s*```"; 
        String regex2 = "SQL query:\\s*(.+?)\\s*(?:\\n|$)"; // Match SQL query preceded by "SQL query:"
        String regex3 = "\\(\\s*(SELECT\\s+.+?\\s+FROM\\s+.+?)\\s*\\)"; // Match SQL query-like text in parentheses

        Pattern pattern1 = Pattern.compile(regex1, Pattern.DOTALL);
        Pattern pattern2 = Pattern.compile(regex2, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Pattern pattern3 = Pattern.compile(regex3, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Pattern pattern4 = Pattern.compile(regex4, Pattern.DOTALL);

        Matcher matcher1 = pattern1.matcher(content);
        Matcher matcher2 = pattern2.matcher(content);
        Matcher matcher3 = pattern3.matcher(content);
        Matcher matcher4 = pattern4.matcher(content);

        // Try different regex patterns to find the SQL query
        if (matcher1.find()) {
            return matcher1.group(1).trim();
        } else if (matcher2.find()) {
            return matcher2.group(1).trim();
        } else if (matcher3.find()) {
            return matcher3.group(1).trim();
        }
            else if (matcher4.find()) {
                return matcher4.group(1).trim();
        } else {
            throw new RuntimeException("Failed to extract SQL query from GPT-3 API response.");
        }
    }

    private String removeParentheses(String input) {
        // Helper method to remove parentheses from the beginning and end of the input string
        return input.replaceAll("^\\s*\\(|\\)\\s*$", "");
    }

    
    public String handleGptResponse(String generatedQuery) {
        try {
            // Extract the SQL query from the GPT-3 API response
            //String sqlQuery = extractSQLQueryFromContent(generatedQuery);

            // Execute the SQL query against your local database
            List<String> tableDetails = executeQueryAndFetchResults(generatedQuery);

            // Prepare a response based on the fetched data
            StringBuilder responseBuilder = new StringBuilder();
            responseBuilder.append("Here are your Search Results:\n\n");
            for (String tableDetail : tableDetails) {
                responseBuilder.append(tableDetail).append("\n");
            }

            return responseBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while processing the request.";
        }
    }
    
    private List<String> executeQueryAndFetchResults(String generatedQuery) throws SQLException {
        // Set up your database connection
        String jdbcUrl = "jdbc:mysql://localhost:3306/employee_self_care_db";
        String username = "root";
        String password = "root";

        List<String> tableDetails = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(generatedQuery);
             ResultSet resultSet = statement.executeQuery()) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Assuming the SQL query returns rows with table details, fetch the data and add it to the list
            while (resultSet.next()) {
                StringBuilder detailBuilder = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String columnValue = resultSet.getString(i);
                    detailBuilder.append(columnName).append(": ").append(columnValue).append(",\n ");
                }
                // Remove the trailing ", " from the last column
                if (detailBuilder.length() >= 2) {
                    detailBuilder.setLength(detailBuilder.length() - 2);
                }
                tableDetails.add(detailBuilder.toString());
            }
        }

        return tableDetails;
    }

    
}

