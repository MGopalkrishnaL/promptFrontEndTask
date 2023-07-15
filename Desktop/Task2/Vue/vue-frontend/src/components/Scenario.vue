<template>
  <div class="container">
    <h1 class="text-center">Scenarios List</h1>
    <select multiple v-model="selectedScenarios" @change="handleScenarioSelection">
      <option v-for="scenario in scenarios" :key="scenario.scenarioId" :value="scenario.scenarioName">{{ scenario.scenarioName }}</option>
    </select>
    <br>
    <select multiple v-model="selectedPrompts" @change="handlePromptSelection">
      <option v-for="prompt in prompts" :key="prompt.promptId" :value="prompt.promptId">{{ prompt.value }}</option>
    </select>
    <br>
    <div>
      <h2>Selected Scenarios:</h2>
      <ul>
        <li v-for="selectedScenario in selectedScenarios" :key="selectedScenario">{{ selectedScenario }}</li>
      </ul>
    </div>
    <div>
      <h2>Selected Prompts:</h2>
      <ul>
        <li v-for="selectedPrompt in selectedPrompts" :key="selectedPrompt">{{ selectedPrompt }}</li>
      </ul>
    </div>
  </div>
</template>

<script>
import ScenariosService from '../services/scenarioService';
import promptService from '@/services/promptService';

export default {
  name: 'ScenariosList',
  data() {
    return {
      scenarios: [],
      selectedScenarios: [],
      prompts: [],
      selectedPrompts: []
    };
  },
  methods: {
    getScenariosList() {
      ScenariosService.getAllScenarios()
        .then((data) => {
          this.scenarios = data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getPromptList() {
      promptService.getPrompt()
        .then((data) => {
          this.prompts = data;
          console.log(this.prompts);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    handleScenarioSelection(event) {
      const selectedOption = event.target.value;
      if (!this.selectedScenarios.includes(selectedOption)) {
        this.selectedScenarios.push(selectedOption);
      }
    },
    handlePromptSelection(event) {
      const selectedOption = event.target.value;
      if (!this.selectedPrompts.includes(selectedOption)) {
        this.selectedPrompts.push(selectedOption);
      }
    }
  },
  created() {
    this.getScenariosList();
    this.getPromptList();
  }
};
</script>
