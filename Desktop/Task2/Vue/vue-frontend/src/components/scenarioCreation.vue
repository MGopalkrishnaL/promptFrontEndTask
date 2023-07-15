<template>
  <div>
    <h2>Scenario Creation</h2>
    <form @submit.prevent="saveScenario2">
      <div class="form-group">
        <label>Scenario</label>
        <input type="text" v-model="Scenario.scenario" class="form-control" placeholder="Describe the Scenario You want to add">
      </div>
      <div class="form-group">
        <label>Scenario Name</label>
        <input type="text" v-model="Scenario.scenarioName" class="form-control" placeholder="Name of the Scenario">
      </div>
      <button type="submit" class="btn btn-primary">Save</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'ScenarioCreation',
  data() {
    return {
      Scenario: {
        scenario: '',
        scenarioName: '',
      },
    };
  },
  methods: {
    saveScenario2() {
      const url = 'http://localhost:8080/saveTheScenario';

      axios.post(url, this.Scenario)
        .then((response) => {
          const audioData = response.data;
          const decodedAudioData = atob(audioData);
          const arrayBuffer = new ArrayBuffer(decodedAudioData.length);
          const view = new Uint8Array(arrayBuffer);
          for (let i = 0; i < decodedAudioData.length; i++) {
            view[i] = decodedAudioData.charCodeAt(i);
          }
          const blob = new Blob([arrayBuffer], { type: 'audio/wav' });
          const audioUrl = URL.createObjectURL(blob);
          const audioPlayer = new Audio(audioUrl);
          audioPlayer.play();

          alert('Save successful');
          this.Scenario.scenario = '';
          this.Scenario.scenarioName = '';
        })
        .catch((error) => {
          alert('Save failed: ' + error.message);
        });
    },
  },
};
</script>
