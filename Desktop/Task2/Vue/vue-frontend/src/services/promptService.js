import axios from "axios";

const SCENARIOS_API_BASE_URL = 'http://localhost:8080/promptList';

class PromptService {
    async getPrompt() {
        try {
            const response = await axios.get(SCENARIOS_API_BASE_URL);
            return response.data;
        } catch (error) {
            console.error("Error fetching scenarios:", error);
            throw error; 
        }
    }
}

export default new PromptService();