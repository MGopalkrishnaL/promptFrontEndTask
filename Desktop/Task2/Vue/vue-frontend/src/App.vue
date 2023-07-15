<template>
  <div id="app">
  <scenario/>
  <scenario-create/>
</div>
</template>

<script>
import Scenario from './components/Scenario.vue'
import ScenarioCreate from './components/scenarioCreation.vue'

export default {
  name: 'App',
  components: {
    Scenario,
    ScenarioCreate
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
