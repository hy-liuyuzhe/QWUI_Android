<template>
    <div class="upgrade-item-container">
        <van-image :src="upgradeIcon" class="upgrade-img"></van-image>
        <div class="upgrade-desc-container">
            <span class="upgrade-desc-title">{{ expTitle }}</span>
            <div class="upgrade-exp-container">
                <van-image :src="require('../assets/lady_level_exp_ic.png')" class="exp-img"></van-image>
                <span class="exp-desc">{{ expDesc }}</span>
            </div>
        </div>
    </div>
</template>
<script>
import Vue from 'vue';
import { Image as VanImage } from 'vant';
import { LADY_CONSTANTS, LADY_UPGRADE_TYPE } from "../constant/constant";

Vue.use(VanImage);
export default {
    name: "UpgradeItem",
    props: {
        type: Number
    },
    computed: {
        upgradeIcon() {
            if (this.type == LADY_UPGRADE_TYPE.OPEN_ROOM) {
                return require('../assets/lady_open_room_ic.png')
            } else {
                return require('../assets/lady_receive_ic.png')
            }
        },
        expTitle() {
            if (this.type == LADY_UPGRADE_TYPE.OPEN_ROOM) {
                return this.$t('lady_open_room_5_min')
            } else {
                return this.$t('lady_receive_gift')
            }
        },
        expDesc() {
            if (this.type == LADY_UPGRADE_TYPE.OPEN_ROOM) {
                return this.$t('lady_exp_day', {exp: LADY_CONSTANTS.EXP_DAY})
            } else {
                return this.$t('lady_diamond_receive', {exp: LADY_CONSTANTS.EXP_DIAMOND})
            }
        }
    }
}
</script>
<style lang="scss" scoped>
.upgrade-item-container {
    width: 622px;
    height: 164px;
    background: rgba(243, 209, 155, 0.2);
    border-radius: 32px;
    display: flex;
    flex-direction: row;
    align-items: center;
}
.upgrade-img {
    width: 104px;
    height: 104px;
    margin-left: 40px;
}
.upgrade-desc-container {
    display: flex;
    flex-direction: column;
    margin-left: 20px;
    .upgrade-desc-title {
        font-size: 32px;
        text-align: left;
        font-family: SFUIText-Bold, SFUIText;
        font-weight: bold;
        color: #C54D23;
        line-height: 38px;
    }
}
.upgrade-exp-container {
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-top: 10px;
    .exp-img {
        width: 48px;
        height: 26px;
    }
    .exp-desc {
        margin-left: 8px;
        font-size: 24px;
        font-family: SFUIText-Regular, SFUIText;
        font-weight: 400;
        color: #C54D23;
    }
}
</style>