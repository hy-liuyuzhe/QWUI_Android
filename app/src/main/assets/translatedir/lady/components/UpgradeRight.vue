<template>
    <div>
        <div class="upgrade-right-container">
            <upgrade-item v-bind:type="1" class="open-room-item"></upgrade-item>
            <upgrade-item v-bind:type="2" class="receive-gift-item"></upgrade-item>
        </div>
    </div>
</template>
<script>
import Vue from 'vue';
import { Image as VanImage } from 'vant';
import UpgradeItem from './UpgradeItem';

Vue.use(VanImage);
export default {
    name: "UpgradeRight",
    components: {
        "upgrade-item": UpgradeItem
    }
}
</script>
<style lang="scss" scoped>
.upgrade-right-container {
    width: 702px;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-top: 70px;
    padding-bottom: 60px;
}
.receive-gift-item {
    margin-top: 24px;
}
</style>