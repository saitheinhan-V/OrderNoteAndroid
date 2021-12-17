package com.note.order.entity

data class OrderItems(
    var orderId: Int,
    var orderStatusId: Int,
    var orderPaymentId: Int,
    var orderStatus: String,
    var paymentStatus: String,
    var paymentDate: String,
    var paymentServiceImgUrl: String,//kpay,wavepay
    var customerName: String,
    var customerId: Int,
    var customerAvatar: String,
    var orderDate: String,
    var productUrl: String,
    var voucherNo: String,
    var totalPrice: Double,
    var totalQty: Int
)
