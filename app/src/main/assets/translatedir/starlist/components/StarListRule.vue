<template>
    <div id="rule-root">
        <div class="rule-container">
            <div class="star-rule-top">
                <span class="star-rule-title">{{ $t('star_rule_title') }}</span>
            </div>
            <div class="rule-detail">
                <p class="rule-content">{{ $t("star_rules_1") }}</p>
                <p class="rule-content">{{ $t("star_rules_2") }}</p>
                <p class="rule-content">{{ $t("star_rules_3") }}</p>
                <p class="rule-content">{{ $t("star_rules_4") }}</p>
            </div>
        </div>
        <van-image class="colse-icon" :src="require('../assets/star_rule_close.png')" @click="onCloseClick"></van-image>
    </div>
</template>
<script lang="ts">
import Vue from 'vue'
import { Image as VanImage } from 'vant';

Vue.use(VanImage);
export default Vue.extend({
    name: "StarListRule",
    methods: {
        onCloseClick() {
          this.$emit("close-btn-click");
        }
    }
})
</script>
<style lang="scss" scoped>
.rule-container {
    width: 600px;
    background: linear-gradient(135deg, #282019 0%, #2E281C 100%);
    border-radius: 32px;
    display: flex;
    flex-direction: column;
    align-items: center;
}
.star-rule-top {
    width: 600px;
    height: 120px;
    background-image: url("../assets/star_rule_top_bg_new.png");
    background-size: 100% 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
}
.star-rule-title {
    font-size: 36px;
    font-family: SFUIText-Bold, SFUIText;
    font-weight: bold;
    color: #F1E5CD;
    margin-top: 48px;
}
.rule-detail {
    margin-top: 16px;
    padding-left: 40px;
    padding-right: 40px;
    padding-bottom: 80px;
}
.rule-content {
    font-size: 28px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    color: #F1E5CD;
    text-align: start;
    margin-top: 0px;
}
.colse-icon {
    width: 70px;
    height: 70px;
    margin-top: 40px;
}
</style>