<script>
import { Radar } from "vue-chartjs";
import axios from 'axios'


export default {
  extends: Radar,
  data () {
    return {
      chartdata: {
        labels: [],
        datasets: [
          {
            label: "태그별 글 작성수",
            backgroundColor: "rgba(179,181,198,0.2)",
            borderColor: "rgba(179,181,198,1)",
            pointBackgroundColor: "rgba(179,181,198,1)",
            pointBorderColor: "#fff",
            pointHoverBackgroundColor: "#fff",
            pointHoverBorderColor: "rgba(179,181,198,1)",
            data: []
          },
        ]
      },
      options: { 
        responsive: true, 
        maintainAspectRatio: false,
        scale: {
          ticks: {
            beginAtZero: true,
            stepSize: 1,
          }
        }
      }
    }
  },
  async mounted() {
    const email = this.$route.params.email
    await axios.get(`http://3.36.125.70:8080/label/myLabel?email=${email}`)
      .then((response) => {
        const data = response.data.slice(0, 7)
        for (var index = 0; index < data.length; index++) {
          if (data[data.length - 1 - index].tagName.length > 15) {
            this.chartdata.labels.push(data[data.length - 1 - index].tagName.slice(0, 15) + '...')
          } else {
            this.chartdata.labels.push(data[data.length - 1 - index].tagName)
          }
          this.chartdata.datasets[0].data.push(data[data.length - 1 - index].num)
        }
        this.renderChart(this.chartdata, this.options)
      })
      .catch((error) => {
        console.log(error)
      })


  }
};
</script>