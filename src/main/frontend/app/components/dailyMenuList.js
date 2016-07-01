import React from 'react';
import DailyMenuItem from './dailyMenuItem';

export default class DailyMenuList extends React.Component {
  render() {
    var component = this;
    var listItems = this.props.data.map(function (item) {
      return (
        <DailyMenuItem key={item.restaurantName}
                       rendered={component.props.selected.filter(s => s.name == item.restaurantName)[0].selected}
                       restaurantName={item.restaurantName}
                       lastUpdated={item.lastUpdated}
                       date={item.date}
                       soups={item.soups}
                       mainDishes={item.mainDishes}
                       other={item.other}/>
      )
    });
    return (
      <div className="daily-menu-list">
        {listItems}
      </div>
    )
  }
}

DailyMenuList.propTypes = {
  data: React.PropTypes.array,
  selected: React.PropTypes.array
};

