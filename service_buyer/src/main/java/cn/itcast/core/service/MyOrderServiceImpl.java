package cn.itcast.core.service;

import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.dao.seller.SellerDao;
import cn.itcast.core.pojo.entity.MyOrder;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import cn.itcast.core.pojo.seller.Seller;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyOrderServiceImpl implements MyOrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private SellerDao sellerDao;
    @Autowired
    private OrderItemDao orderItemDao;

    @Override
    public List<MyOrder> search( String userName) {

        OrderQuery orderQuery = new OrderQuery();
        OrderQuery.Criteria criteria = orderQuery.createCriteria();
        criteria.andUserIdEqualTo(userName);

        List<Order> orderList = orderDao.selectByExample(orderQuery);

        if (orderList != null){
            List<MyOrder> myOrderList = new ArrayList<>();
            for (Order order : orderList) {
                MyOrder myOrder = new MyOrder();
                Seller seller = sellerDao.selectByPrimaryKey(order.getSellerId());
                myOrder.setSellerName(seller.getNickName());
                myOrder.setOrderId(order.getOrderId());
                myOrder.setCreateTime(order.getCreateTime());
                myOrder.setPayment(order.getPayment());
                myOrder.setStatus(order.getStatus());

                OrderItemQuery orderItemQuery = new OrderItemQuery();

                orderItemQuery.createCriteria().andOrderIdEqualTo(order.getOrderId());
                List<OrderItem> orderItemList = orderItemDao.selectByExample(orderItemQuery);
                if (orderItemList != null){
                    myOrder.setOrderItemList(orderItemList);
                }
                myOrderList.add(myOrder);
            }
            return myOrderList;
        }
        return null;
    }
}
