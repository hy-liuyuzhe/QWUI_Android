export const CURRENCY_TYPE = {
    COINS: 0, //金币
};

export const getVipMedal = (vipLevel) => {
    if (vipLevel < 7 && vipLevel > 0) {
        return `https://wayak-resource.wenext.media/vip/new_vip_medal_${vipLevel}.png`;
    } else {
        return ""
    }
}

export const MerchantMessage = {
    warning: 1,
    cancelDays: 2,
    cancelForever: 3
};

export const MERCHANT_MESSAGE_TYPE = 6;