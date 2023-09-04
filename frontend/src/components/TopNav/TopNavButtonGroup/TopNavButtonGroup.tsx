import { useLocation, useNavigate } from "react-router-dom";
import { useRecoilState, useSetRecoilState } from "recoil";
import styled from "styled-components";
import {
  currentCabinetIdState,
  myCabinetInfoState,
  targetCabinetInfoState,
  targetUserInfoState,
  userState,
} from "@/recoil/atoms";
import TopNavButton from "@/components/TopNav/TopNavButtonGroup/TopNavButton/TopNavButton";
import { CabinetInfo, MyCabinetInfoResponseDto } from "@/types/dto/cabinet.dto";
import { LentDto } from "@/types/dto/lent.dto";
import { UserInfo } from "@/types/dto/user.dto";
import { UserDto } from "@/types/dto/user.dto";
import CabinetStatus from "@/types/enum/cabinet.status.enum";
import CabinetType from "@/types/enum/cabinet.type.enum";
import {
  axiosCabinetById,
  axiosDeleteCurrentBanLog,
  axiosMyInfo,
  axiosMyLentInfo,
} from "@/api/axios/axios.custom";
import useMenu from "@/hooks/useMenu";

const getDefaultCabinetInfo = (myInfo: UserDto): CabinetInfo => ({
  building: "",
  floor: 0,
  cabinetId: 0,
  visibleNum: 0,
  lentType: CabinetType.PRIVATE,
  title: null,
  maxUser: 0,
  status: CabinetStatus.AVAILABLE,
  section: "",
  lents: [
    {
      userId: myInfo.userId,
      name: myInfo.name,
      lentHistoryId: 0,
      startedAt: new Date(),
      expiredAt: new Date(),
    },
  ] as LentDto[],
  statusNote: "",
});

const TopNavButtonGroup = ({ isAdmin }: { isAdmin?: boolean }) => {
  const { toggleCabinet, toggleMap, openCabinet, closeAll } = useMenu();
  const [currentCabinetId, setCurrentCabinetId] = useRecoilState(
    currentCabinetIdState
  );

  const setMyLentInfo =
    useSetRecoilState<MyCabinetInfoResponseDto>(myCabinetInfoState);
  const [targetCabinetInfo, setTargetCabinetInfo] = useRecoilState<CabinetInfo>(
    targetCabinetInfoState
  );
  const [myInfo, setMyInfo] = useRecoilState(userState);
  const [myInfoData, setMyInfoData] =
    useRecoilState<UserInfo>(targetUserInfoState);
  const { pathname } = useLocation();
  const navigator = useNavigate();

  const setTargetCabinetInfoToMyCabinet = async () => {
    if (myInfo.cabinetId === null) {
      const defaultCabinetInfo = getDefaultCabinetInfo(myInfo);
      setTargetCabinetInfo(defaultCabinetInfo);
      setCurrentCabinetId(0);
      return;
    }
    setCurrentCabinetId(myInfo.cabinetId);
    setMyInfoData(myInfoData);
    try {
      const { data } = await axiosCabinetById(myInfo.cabinetId);
      setTargetCabinetInfo(data);
    } catch (error) {
      console.log(error);
    }
  };

  const clickMyCabinet = async () => {
    try {
      const { data: myInfo } = await axiosMyInfo();
      setMyInfo(myInfo);
    } catch (error: any) {
      throw error;
    } finally {
      if (myInfo.cabinetId === null) {
        setTargetCabinetInfoToMyCabinet();
        toggleCabinet();
      } else if (currentCabinetId !== myInfo.cabinetId) {
        setTargetCabinetInfoToMyCabinet();
        openCabinet();
      } else {
        toggleCabinet();
      }
      try {
        const { data: myLentInfo } = await axiosMyLentInfo();
        if (myLentInfo) {
          setMyLentInfo(myLentInfo);
          setMyInfo({ ...myInfo, cabinetId: myLentInfo.cabinetId });
          setTargetCabinetInfo(myLentInfo);
        }
      } catch (error) {
        throw error;
      }
    }
  };

  const searchBarOn = () => {
    document.getElementById("searchBar")!.classList.add("on");
    document.getElementById("topNavLogo")!.classList.add("pushOut");
    document.getElementById("topNavButtonGroup")!.classList.add("pushOut");
    document.getElementById("topNavWrap")!.classList.add("pushOut");
  };

  const clickSearchButton = () => {
    if (!pathname.includes("search")) navigator("search");
    closeAll();
    searchBarOn();
  };

  return (
    <NaviButtonsStyled id="topNavButtonGroup">
      {import.meta.env.VITE_UNBAN === "true" && (
        <TopNavButton
          onClick={() => axiosDeleteCurrentBanLog(myInfo.userId)}
          imgSrc="/src/assets/images/happyCcabiWhite.png"
          width="32px"
          height="32px"
        />
      )}
      {isAdmin && (
        <TopNavButton
          id="searchButton"
          onClick={clickSearchButton}
          imgSrc="/src/assets/images/searchWhite.svg"
          width="28px"
          height="28px"
          disable={true}
        />
      )}
      {!isAdmin && (
        <TopNavButton
          onClick={clickMyCabinet}
          imgSrc="/src/assets/images/myCabinetIcon.svg"
        />
      )}
      <TopNavButton onClick={toggleMap} imgSrc="/src/assets/images/map.svg" />
    </NaviButtonsStyled>
  );
};

const NaviButtonsStyled = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  & > div:last-child {
    margin-right: 0;
  }
`;

export default TopNavButtonGroup;
