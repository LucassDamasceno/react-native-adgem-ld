import * as React from 'react';

import { StyleSheet, View, Button } from 'react-native';
import * as adgem from 'react-native-adgem-ld';

export default function App() {
  React.useEffect(() => {
    adgem.addListener('RewardOfferWall', ({ event }): any => {
      console.log('RewardOfferWall', event);
    });
    adgem.addListener('CloseOfferWall', ({ event }): any => {
      console.log('CloseOfferWall', event);
    });
    adgem.addListener('ChangedOfferWall', ({ event }): any => {
      console.log('ChangedOfferWall', event);
    });
  }, []);

  return (
    <View style={styles.container}>
      <Button
        title="Initialize adgem"
        onPress={() => {
          adgem.initializeAdgem();
        }}
      />

      <Button
        title="showOfferWall"
        onPress={() => {
          adgem.showOfferWall();
        }}
      />

      <Button
        title="Get State"
        onPress={async () => {
          console.log(await adgem.getOfferWallState());
        }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
