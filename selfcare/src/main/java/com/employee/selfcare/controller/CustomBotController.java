package com.employee.selfcare.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.employee.selfcare.entites.ChatGPTRequest;
import com.employee.selfcare.entites.DateRange;
import com.employee.selfcare.entites.Employee;
import com.employee.selfcare.entites.LeaveApplied;
import com.employee.selfcare.entites.LeaveBalance;
import com.employee.selfcare.repositories.EmployeeRepository;
import com.employee.selfcare.repositories.LeaveAppliedRepository;
import com.employee.selfcare.repositories.LeaveBalanceRepository;
import com.employee.selfcare.services.GptService;
import com.employee.selfcare.entites.ChatGPTRequest;
import com.employee.selfcare.entites.ChatGptResponse;



@RestController
@RequestMapping("/bot")
public class CustomBotController {

    @Value("${openai.model}")
    private String model;

    @Value(("${openai.api.url}"))
    private String apiURL;

    @Autowired
    private RestTemplate template;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;
    
    @Autowired
    private LeaveAppliedRepository leaveAppliedRepository;
    
    @Autowired
    private GptService gptService;

    @GetMapping("/generate-sql-query")
    public String generateSQLQuery(@RequestParam("searchText") String searchText) {
        return gptService.generateSQLQuery(searchText);
    }
    
    
    
    @GetMapping("/chat")
    public String chat(@RequestParam("prompt") String prompt){
        ChatGPTRequest request=new ChatGPTRequest(model, prompt);
        com.employee.selfcare.entites.ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        return chatGptResponse.getChoices().get(0).getMessage().getContent();
    }

    @GetMapping("/chats")
    public String chats(@RequestParam("prompt") String prompt) {
        String empId = extractEmployeeIdFromPrompt(prompt);

        if (empId == null) {
            return "Invalid prompt format. Please provide a valid Employee ID.";
        }

        // Convert empId to a Long type.
        Long employeeId = Long.parseLong(empId);
        // Retrieve the leave balance for the employee.
        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeNative(employeeId);

        if (leaveBalance == null) {
            return "Leave balance not available for this employee.";
        }

        // Construct the prompt for the GPT-3.5 API call with the extracted employee ID.
        String apiPrompt = "What is the remaining leave balance for Employee ID: [" + employeeId + "]?";
        String encodedPrompt = encodePrompt(apiPrompt);
       
        // Pass the constructed prompt to GPT-3.5 API and return the response.
        ChatGPTRequest request = new ChatGPTRequest(model, apiPrompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        return chatGptResponse.getChoices().get(0).getMessage().getContent();
    }
    
    private String extractEmployeeIdFromPrompt(String prompt) {
        // Check if the prompt contains the "Employee ID:" prefix.
        int prefixIndex = prompt.indexOf("Employee ID:");
        if (prefixIndex == -1) {
            // If the prefix is not found, return null or an appropriate error message.
            return null;
        }

        // Extract the substring after the "Employee ID:" prefix.
        String employeeIdPart = prompt.substring(prefixIndex + "Employee ID:".length()).trim();

        // Check if the extracted part contains the employee ID in square brackets.
        if (employeeIdPart.startsWith("[") && employeeIdPart.endsWith("]")) {
            // Extract the employee ID from inside the square brackets.
            String employeeId = employeeIdPart.substring(1, employeeIdPart.length() - 1);
            // Return the extracted employee ID.
            return employeeId.trim();
        }

        // If the employee ID is not found in the expected format, return null or an appropriate error message.
        return null;
    }

    @GetMapping("/chatss")
    public String chatsss(@RequestParam("prompt") String prompt) {
        // Extract the employee ID from the prompt. You need to define the format of the prompt to include the employee ID.
        Long employeeId = extractEmployeeIdFromPrompts(prompt);

        if (employeeId == null) {
            return "Invalid prompt format. Please provide a valid Employee ID.";
        }

        // Construct the prompt for the GPT-3.5 API call with the extracted employee ID.
        String apiPrompt = "What is the remaining leave balance for Employee ID: [" + employeeId + "]?";

        // Pass the constructed prompt to GPT-3.5 API and return the response.
        ChatGPTRequest request = new ChatGPTRequest(model, apiPrompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        return chatGptResponse.getChoices().get(0).getMessage().getContent();
    }

    private Long extractEmployeeIdFromPrompts(String prompt) {
        // Check if the prompt contains the "Employee ID:" prefix.
        int prefixIndex = prompt.indexOf("Employee ID:");
        if (prefixIndex == -1) {
            // If the prefix is not found, return null or an appropriate error value (e.g., -1).
            return null;
        }

        // Extract the substring after the "Employee ID:" prefix.
        String employeeIdPart = prompt.substring(prefixIndex + "Employee ID:".length()).trim();

        // Check if the extracted part contains the employee ID in square brackets.
        if (employeeIdPart.startsWith("[") && employeeIdPart.endsWith("]")) {
            try {
                // Extract the employee ID from inside the square brackets and parse it to a long.
                String employeeId = employeeIdPart.substring(1, employeeIdPart.length() - 1);
                return Long.parseLong(employeeId.trim());
            } catch (NumberFormatException e) {
                // If the employee ID is not a valid number, return null or an appropriate error value (e.g., -1).
                return null;
            }
        }

        // If the employee ID is not found in the expected format, return null or an appropriate error value (e.g., -1).
        return null;
    }

    private String encodePrompt(String prompt) {
        try {
            // Encode the prompt using UTF-8 encoding.
            String encodedPrompt = URLEncoder.encode(prompt, "UTF-8");
            return encodedPrompt;
        } catch (UnsupportedEncodingException e) {
            // Handle the exception if encoding fails.
            e.printStackTrace();
            return null;
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //////
    
    @GetMapping("/chatt")
    public String chatss(@RequestParam("prompt") String prompt) {
        String empId = extractEmployeeIdFromPromptss(prompt);

        if (empId == null) {
            return "Invalid prompt format. Please provide a valid Employee ID.";
        }

        // Encode the entire prompt to the desired format (including the Employee ID).
        String encodedPrompt = encodePrompts(prompt);

        if (encodedPrompt == null) {
            return "Error encoding the prompt.";
        }

        // Pass the encoded prompt to GPT-3.5 API and return the response.
        ChatGPTRequest request = new ChatGPTRequest(model, encodedPrompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        String gptResponse = chatGptResponse.getChoices().get(0).getMessage().getContent();

        // Retrieve the employee from the database based on the employee ID.
        Long employeeId = Long.parseLong(empId);
        Employee employee = employeeRepository.findByEmpId(employeeId);

        if (employee == null) {
            return "Employee not found.";
        }

        // Retrieve the leave balance for the employee.
        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeNative(employeeId);

        if (leaveBalance == null) {
            return "Leave balance not available for this employee.";
        }

        // Combine the GPT response with the leave balance information.
        String result = gptResponse + "\nLeave Balance: " + leaveBalance.getRemainingLeaves();
        return result;
    }


    private String extractEmployeeIdFromPromptss(String prompt) {
        // Check if the prompt contains the "Employee ID:[" prefix.
        int prefixIndex = prompt.indexOf("Employee ID:[");
        if (prefixIndex == -1) {
            // If the prefix is not found, return null or an appropriate error message.
            return null;
        }

        // Extract the substring after the "Employee ID:[" prefix.
        String employeeIdPart = prompt.substring(prefixIndex + "Employee ID:[".length()).trim();

        // Check if the extracted part contains the closing square bracket.
        int closingBracketIndex = employeeIdPart.indexOf("]");
        if (closingBracketIndex == -1) {
            // If the closing bracket is not found, return null or an appropriate error message.
            return null;
        }

        // Extract the employee ID from inside the square brackets.
        String employeeId = employeeIdPart.substring(0, closingBracketIndex);

        // Return the extracted employee ID.
        return employeeId.trim();
    }

    private String encodePrompts(String prompt) {
        try {
            // Encode the prompt using UTF-8 encoding.
            String encodedPrompt = URLEncoder.encode(prompt, "UTF-8");
            return encodedPrompt;
        } catch (UnsupportedEncodingException e) {
            // Handle the exception if encoding fails.
            e.printStackTrace();
            return null;
        }
    }
    
  ///////
    
    @GetMapping("/leave")
    public String leave(@RequestParam("prompt") String prompt) {
        String empId = extractEmployeeIdFromPromptss(prompt);

        if (empId == null) {
            return "Invalid prompt format. Please provide a valid Employee ID.";
        }

        // Extract date ranges from the prompt
        DateRange dateRange = extractDateRangesFromPrompt(prompt);

        if (dateRange == null || !dateRange.isValid()) {
            return "Invalid date range format. Please provide valid date ranges.";
        }

        // Encode the entire prompt to the desired format (including the Employee ID).
        String encodedPrompt = encodePrompts(prompt);

        if (encodedPrompt == null) {
            return "Error encoding the prompt.";
        }

        // Pass the encoded prompt to GPT-3.5 API and return the response.
        ChatGPTRequest request = new ChatGPTRequest(model, encodedPrompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        String gptResponse = chatGptResponse.getChoices().get(0).getMessage().getContent();

        // Retrieve the employee from the database based on the employee ID.
        Long employeeId = Long.parseLong(empId);
        Employee employee = employeeRepository.findByEmpId(employeeId);

        if (employee == null) {
            return "Employee not found.";
        }

        // Retrieve the leave balance for the employee.
        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeNative(employeeId);

        if (leaveBalance == null) {
            return "Leave balance not available for this employee.";
        }

        // Create the leave request and save it to the database.
        LeaveApplied leaveApplied = new LeaveApplied();
        leaveApplied.setEmployee(employee);
        leaveApplied.setStartDate(dateRange.getStartDate());
        leaveApplied.setEndDate(dateRange.getEndDate());
        leaveApplied.setReason("Vacation"); // Set the appropriate reason for the leave
        leaveApplied.setStatus("Pending"); // Set the initial status to "Pending"

        // Save the leave request to the database
        leaveAppliedRepository.save(leaveApplied);

        // Combine the GPT response with the leave balance information and date range.
        String result = gptResponse + "\nLeave Balance: " + leaveBalance.getRemainingLeaves();
        result += "\nLeave Request created for Employee ID: " + empId + " from " + dateRange.getStartDate() + " to " + dateRange.getEndDate();

        return result;
    }

    private DateRange extractDateRangesFromPrompt(String prompt) {
        // Check if the prompt contains the "Date Range:[" prefix.
        int prefixIndex = prompt.indexOf("Date Range:[");
        if (prefixIndex == -1) {
            // If the prefix is not found, return null or an appropriate error message.
            return null;
        }

        // Extract the substring after the "Date Range:[" prefix.
        String dateRangePart = prompt.substring(prefixIndex + "Date Range:[".length()).trim();

        // Check if the extracted part contains the closing square bracket.
        int closingBracketIndex = dateRangePart.indexOf("]");
        if (closingBracketIndex == -1) {
            // If the closing bracket is not found, return null or an appropriate error message.
            return null;
        }

        // Extract the date range from inside the square brackets.
        String dateRangeStr = dateRangePart.substring(0, closingBracketIndex);

        // Split the date range into start and end date
        String[] dateRangeArr = dateRangeStr.split(" to ");
        if (dateRangeArr.length != 2) {
            return null; // Invalid format
        }

        String startDateStr = dateRangeArr[0].trim();
        String endDateStr = dateRangeArr[1].trim();

        // Parse the start and end date using SimpleDateFormat with "yyyy-MM-dd" format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate, endDate;

        try {
            startDate = dateFormat.parse(startDateStr);
            endDate = dateFormat.parse(endDateStr);
        } catch (ParseException e) {
            // Handle parsing errors
            e.printStackTrace();
            return null;
        }

        // Return the DateRange object with parsed start and end dates
        return new DateRange(startDate, endDate);
    }

////
    @GetMapping("/employeees")
    public String getEmployeeDetails(@RequestParam("prompt") String prompt) {
        String empId = extractEmployeeIdFromPromptss(prompt);

        if (empId == null) {
            return "Invalid prompt format. Please provide a valid Employee ID.";
        }

        // Retrieve the employee from the database based on the employee ID.
        Long employeeId = Long.parseLong(empId);
        Employee employee = employeeRepository.findByEmpId(employeeId);

        if (employee == null) {
            return "Employee not found.";
        }

        // Encode the prompt without the Employee ID for GPT-3.5 API.
        String promptWithoutEmployeeId = prompt.replace("Employee ID:[" + empId + "]", "");
        String encodedPrompt = encodePrompts(promptWithoutEmployeeId);

        if (encodedPrompt == null) {
            return "Error encoding the prompt.";
        }

        // Pass the encoded prompt to GPT-3.5 API and return the response.
        ChatGPTRequest request = new ChatGPTRequest(model, encodedPrompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        String gptResponse = chatGptResponse.getChoices().get(0).getMessage().getContent();

        // Combine the GPT response with the employee details.
        String result = "Employee Details:\n" +
                "Employee ID: " + employee.getEmpId() + "\n" +
                "Name: " + employee.getName() + "\n" +
                "Current Designation: " + employee.getCurrentDesignation() + "\n" +
                "Gender: " + employee.getGender() + "\n" +
                "Interest Area: " + employee.getInterestArea() + "\n" +
                "Tools: " + employee.getTools() + "\n" +
                "Years of Experience: " + employee.getYearsExperience() + "\n" +
                // Add other attributes here if needed.
                // ...

                // You can customize the response format as needed.
                "\n" + gptResponse;

        return result;
    }

/////
    
    @GetMapping("/employees")
    public String getEmployeeDetails(@RequestParam(value = "id", required = false) Long id,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "currentDesignation", required = false) String currentDesignation,
                                     @RequestParam(value = "interestArea", required = false) String interestArea,
                                     @RequestParam(value = "tools", required = false) String tools,
                                     @RequestParam(value = "yearsExperience", required = false) Integer yearsExperience,
                                     @RequestParam(value = "prompt", required = false) String prompt) {
        // If the prompt is provided, extract the employee ID from it.
        String empId = null;
        if (prompt != null) {
            empId = extractEmployeeIdFromPromptss(prompt);
            if (empId == null) {
                return "Invalid prompt format. Please provide a valid Employee ID.";
            }
        }

        // Retrieve the employee from the database based on the provided filter criteria.
        Employee employee = null;
        if (id != null) {
            employee = employeeRepository.findByEmpId(id);
        } else if (name != null) {
            employee = employeeRepository.findByName(name);
        } else if (currentDesignation != null) {
            employee = employeeRepository.findByCurrentDesignation(currentDesignation);
        } else if (interestArea != null) {
            employee = employeeRepository.findByInterestArea(interestArea);
        } else if (tools != null) {
            employee = employeeRepository.findByTools(tools);
        } else if (yearsExperience != null) {
            employee = employeeRepository.findByYearsExperience(yearsExperience);
        }

        if (employee == null) {
            return "Employee not found.";
        }

        // Encode the prompt without the Employee ID for GPT-3.5 API.
        String promptWithoutEmployeeId = prompt != null ? prompt.replace("Employee ID:[" + empId + "]", "") : null;
        String encodedPrompt = promptWithoutEmployeeId != null ? encodePrompts(promptWithoutEmployeeId) : null;

        if (encodedPrompt == null) {
            return "Error encoding the prompt.";
        }

        // Pass the encoded prompt to GPT-3.5 API and return the response.
        ChatGPTRequest request = new ChatGPTRequest(model, encodedPrompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        String gptResponse = chatGptResponse.getChoices().get(0).getMessage().getContent();

        // Combine the GPT response with the employee details.
        String result = "Employee Details:\n" +
                        "Employee ID: " + employee.getEmpId() + "\n" +
                        "Name: " + employee.getName() + "\n" +
                        "Current Designation: " + employee.getCurrentDesignation() + "\n" +
                        "Gender: " + employee.getGender() + "\n" +
                        "Interest Area: " + employee.getInterestArea() + "\n" +
                        "Tools: " + employee.getTools() + "\n" +
                        "Years of Experience: " + employee.getYearsExperience() + "\n" +
                        // Add other attributes here if needed.
                        // ...

                        // You can customize the response format as needed.
                        "\n" + gptResponse;

        return result;
    }

  //////////////////
    
    
    @GetMapping("/employeess")
    public String getEmployeeDetailss(@RequestParam(value = "prompt", required = false) String prompt) {
        if (prompt == null) {
            return "Please provide a prompt containing the required information.";
        }

        // Extract the employee ID and other filter criteria from the prompt.
        String empIdFilter = extractEmployeeIdFromPromptss(prompt);
        Long idFilter = null;
        if (empIdFilter != null) {
            try {
                idFilter = Long.parseLong(empIdFilter);
            } catch (NumberFormatException e) {
                return "Invalid Employee ID format.";
            }
        }
        
        String nameFilter = extractFilterFromPrompt(prompt, "name");
        String currentDesignationFilter = extractFilterFromPrompt(prompt, "currentDesignation");
        String interestAreaFilter = extractFilterFromPrompt(prompt, "interestArea");
        String toolsFilter = extractFilterFromPrompt(prompt, "tools");
        Integer yearsExperienceFilter = extractYearsExperienceFromPrompt(prompt);

        // Fetch employees based on the filter criteria from the database.
        List<Employee> employees = employeeRepository.findAllByFilters(idFilter, nameFilter, currentDesignationFilter, interestAreaFilter, toolsFilter, yearsExperienceFilter);

        if (employees.isEmpty()) {
            return "Employee not found.";
        }

        // Choose the first employee from the list (you can implement your logic here).
        Employee employee = employees.get(0);

        // Encode the prompt without the Employee ID and filter criteria for GPT-3.5 API.
        String promptWithoutFilters = removeFilterFromPrompt(prompt, "id");
        promptWithoutFilters = removeFilterFromPrompt(promptWithoutFilters, "name");
        promptWithoutFilters = removeFilterFromPrompt(promptWithoutFilters, "currentDesignation");
        promptWithoutFilters = removeFilterFromPrompt(promptWithoutFilters, "interestArea");
        promptWithoutFilters = removeFilterFromPrompt(promptWithoutFilters, "tools");
        promptWithoutFilters = removeFilterFromPrompt(promptWithoutFilters, "yearsExperience");

        String encodedPrompt = encodePrompts(promptWithoutFilters);

        if (encodedPrompt == null) {
            return "Error encoding the prompt.";
        }

        // Pass the encoded prompt to GPT-3.5 API and return the response.
        ChatGPTRequest request = new ChatGPTRequest(model, encodedPrompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        String gptResponse = chatGptResponse.getChoices().get(0).getMessage().getContent();

        // Combine the GPT response with the employee details.
        String result = "Employee Details:\n" +
                        "Employee ID: " + employee.getEmpId() + "\n" +
                        "Name: " + employee.getName() + "\n" +
                        "Current Designation: " + employee.getCurrentDesignation() + "\n" +
                        "Gender: " + employee.getGender() + "\n" +
                        "Interest Area: " + employee.getInterestArea() + "\n" +
                        "Tools: " + employee.getTools() + "\n" +
                        "Years of Experience: " + employee.getYearsExperience() + "\n" +
                        // Add other attributes here if needed.
                        // ...

                        // You can customize the response format as needed.
                        "\n" + gptResponse;

        return result;
    }

    private String extractFilterFromPrompt(String prompt, String filterName) {
        int startIndex = prompt.indexOf(filterName + "=");
        if (startIndex == -1) {
            return null;
        }

        startIndex += filterName.length() + 1; // Add 1 to skip '='
        int endIndex = prompt.indexOf("&", startIndex);
        if (endIndex == -1) {
            endIndex = prompt.length();
        }

        return prompt.substring(startIndex, endIndex);
    }

    private Integer extractYearsExperienceFromPrompt(String prompt) {
        String yearsExperienceFilter = extractFilterFromPrompt(prompt, "yearsExperience");
        if (yearsExperienceFilter == null) {
            return null;
        }

        try {
            return Integer.parseInt(yearsExperienceFilter);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String removeFilterFromPrompt(String prompt, String filterName) {
        int startIndex = prompt.indexOf(filterName + "=");
        if (startIndex == -1) {
            return prompt;
        }

        int endIndex = prompt.indexOf("&", startIndex);
        if (endIndex == -1) {
            endIndex = prompt.length();
        } else {
            endIndex++; // Include '&' in endIndex to remove it.
        }

        return prompt.substring(0, startIndex) + prompt.substring(endIndex);
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}