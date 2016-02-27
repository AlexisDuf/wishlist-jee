import Colors             from 'material-ui/lib/styles/colors'
import ColorManipulator   from 'material-ui/lib/utils/color-manipulator'
import Spacing            from 'material-ui/lib/styles/spacing'

export default {
  spacing: Spacing,
  fontFamily: 'Muli, sans-serif',
  palette: {
    primary1Color: Colors.indigo500,
    primary2Color: Colors.cyan700,
    primary3Color: Colors.lightBlack,
    accent1Color: Colors.indigo500,
    accent2Color: Colors.grey100,
    accent3Color: Colors.grey500,
    textColor: "#0D47A1",
    alternateTextColor: "white",
    canvasColor: Colors.white,
    borderColor: Colors.grey300,
    disabledColor: ColorManipulator.fade(Colors.darkBlack, 0.3),
    backgroundColor:"#42A5F5"
  },
};
