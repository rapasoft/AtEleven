import React from 'react'
import DailyMenuItem from './dailyMenuItem'

export default class DailyMenuList extends React.Component {
  render() {
    var listItems = this.props.data.map(function (item) {
      return (
        <DailyMenuItem key={item.restaurantName}
                       restaurantName={item.restaurantName}
                       date={item.date}
                       soups={item.soups}
                       mainDishes={item.mainDishes}
                       other={item.other}/>
      );
    });
    return (
      <div className="daily-menu-list">
        {listItems}
      </div>
    )
  }
}

