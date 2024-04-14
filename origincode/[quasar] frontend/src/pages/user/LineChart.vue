<script>
import { Line } from "vue-chartjs";
import axios from 'axios'

export default {
  extends: Line,
  data () {
    return {
      chartdata: {
        labels: [],
        datasets: [
          {
            label: "월별 글 작성수",
            data: [],
            backgroundColor: "transparent",
            borderColor: "#512da8",
            pointBackgroundColor: "rgba(171, 71, 188, 1)"
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          yAxes: [{
            ticks: {
              min: 0,
              stepSize: 1,
              reverse: false,
              beginAtZero: true
            }
          }]
        }
      }
    }
  },
  async mounted() {
    const email = this.$route.params.email
    await axios.get(`http://3.36.125.70:8080/notice/MonthlyPosts?email=${email}`)
      .then((response) => {
        for (var index = 0; index < response.data.length; index++) {
          this.chartdata.labels.push(response.data[6 - index].dateTime)
          this.chartdata.datasets[0].data.push(response.data[6 - index].num)
        }
        this.renderChart(this.chartdata, this.options)
      })
      .catch((error) => {
        console.log(error)
      })
  },
 
};
</script>