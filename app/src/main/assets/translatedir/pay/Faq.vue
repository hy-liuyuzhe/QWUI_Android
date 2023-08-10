<template>
    <div class="faq-container">
        <van-tabs 
            v-model="active"
            color="#00E0D7"
            title-active-color="#222222" 
            title-inactive-color="#AAAAAA"
            line-height="3px"
            line-width="32px"
            sticky
            :swipeable="true"
            >
            <van-tab :title="$t('gold_tab')"><recharge-faq/></van-tab>
            <van-tab :title="$t('diamond_tab')"><income-faq/></van-tab>
        </van-tabs>
        <div class="feedback-btn" @click="onFeedbackClick">
          <span class="feedback-title">{{ $t('feed_back') }}</span>
        </div>
    </div>
</template>
<script>
import Vue from 'vue';
import { Tab, Tabs } from 'vant';
import RechargeFaq from './components/RechargeFaq.vue';
import IncomeFaq from './components/IncomeFaq.vue';
import { goFeedback } from "../../common/bridge/index";

Vue.use(Tab);
Vue.use(Tabs);

export default {
    name: "Faq",
    components: {
      'recharge-faq': RechargeFaq,
      'income-faq': IncomeFaq,
    },
    data() {
        return {
            active: Number(this.$route.params.index)
        }; 
    },
    methods: {
      async onFeedbackClick() {
        goFeedback({
          type: "1"
        });
      },
    },
    metaInfo: {
    title: "FAQ",
    meta: [
      {
        name: "viewport",
        content: "width=device-width, initial-scale=1, maximum-scale=1",
      },
    ],
  },
}
</script>
<style lang="scss" scoped>
.faq-container {
    width: 750px;
    text-align: start;
}
.feedback-btn {
  width: 638px;
  height: 96px;
  background: #00E0D7;
  border-radius: 48px;
  position: fixed;
  bottom: 40px;
  text-align: center;
  left: 56px;
}
.feedback-title {
  height: 38px;
  font-size: 32px;
  font-family: SFUIText-Semibold, SFUIText;
  font-weight: 600;
  color: #FFFFFF;
  line-height: 38px;
  position: relative;
  top: 29px;
}
</style>