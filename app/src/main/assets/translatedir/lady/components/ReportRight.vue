<template>
    <div class="report-right-container">
        <van-image :src="require('../assets/lady_customer_service_ic.png')" class="customer-service-img"></van-image>
        <span class="report-desc">{{ $t('lady_report_right_desc') }}</span>
    </div>
</template>
<script>
import Vue from 'vue';
import { Image as VanImage } from 'vant';

Vue.use(VanImage);
export default {
    name: "ReportRight"
}
</script>
<style lang="scss" scoped>
.report-right-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-top: 70px;
    padding-bottom: 60px;
}
.customer-service-img {
    width: 160px;
    height: 160px;
}
.report-desc {
    margin-top: 20px;
    width: 622px;
    font-size: 28px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    color: #C54D23;
    text-align: start;
}
</style>