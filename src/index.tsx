import { NativeModules, Platform, NativeEventEmitter } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-adgem' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const Adgem = NativeModules.AdgemLd
  ? NativeModules.AdgemLd
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

const eventEmitter = new NativeEventEmitter(Adgem);

export function multiply(a: number, b: number): Promise<number> {
  return Adgem.multiply(a, b);
}

export function initializeAdgem(playerId: string): Promise<number> {
  return Adgem.initializeAdgem(playerId);
}
export function showOfferWall(): Promise<number> {
  return Adgem.showOfferWall();
}
export function getOfferWallState(): Promise<any> {
  return Adgem.getOfferWallState();
}

type EventType = 'ChangedOfferWall' | 'RewardOfferWall' | 'CloseOfferWall';

export const addListener = (type: EventType, handler: (value: any) => {}) => {
  eventEmitter.addListener(type, handler);
};

export const removeAllListeners = () => {
  eventEmitter.removeAllListeners('ChangedOfferWall');
  eventEmitter.removeAllListeners('RewardOfferWall');
  eventEmitter.removeAllListeners('CloseOfferWall');
};
